package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";


    private AuthenticatedUser currentUser;
    private int userId;
    private Account userAccount;
    private BigDecimal userBalance;


    //services
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private TransactionService transactionService = new TransactionService(API_BASE_URL);
    private AccountService accountService = new AccountService(API_BASE_URL);
    private UserService userService = new UserService(API_BASE_URL);


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
        accountService.setAUTH_TOKEN(currentUser.getToken());
        transactionService.setAUTH_TOKEN(currentUser.getToken());
        userService.setAUTH_TOKEN(currentUser.getToken());
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }


	private void viewCurrentBalance() {
        consoleService.printBalanceMessage(accountService.getCurrentUserAccount());

    }

	private void viewTransferHistory() {
        userAccount = accountService.getCurrentUserAccount();
        Transfer[] transfers = transactionService.get(userAccount.getAccountId());
        if (transfers.length != 0) {
            consoleService.printPastTransfer(transfers, userAccount);
            int transferId = consoleService.promptForTransferId();
            Transfer transfer = transactionService.getById(transferId);
            if (transfer != null) {
                consoleService.printTransferDetails(transactionService.getById(transferId), userAccount);
            } else {
                consoleService.printTransactionNotFoundMessage();
            }
        } else {
            consoleService.printTransactionNotFoundMessage();
        }
    }

	private void viewPendingRequests() {
        Account userAccount = accountService.getCurrentUserAccount();
        Transfer[] pendingTransfers = transactionService.getPending(userAccount.getAccountId());
        if(pendingTransfers.length == 0) {
            consoleService.printTransactionNotFoundMessage();
        } else {
            consoleService.printPendingTransfer(pendingTransfers);
            int pendingId = consoleService.promptForPendingId();
            Transfer pendingTransfer = transactionService.getById(pendingId);
            if (pendingTransfer != null) {
                String decision = consoleService.printPendingTransferDecision();
                if (Objects.equals(decision, "1")) {
                    pendingTransfer.setStatusId(2);
                    userAccount.setBalance(userAccount.getBalance().subtract(pendingTransfer.getAmount()));
                    Account accountTo = accountService.getByAccountId(pendingTransfer.getAccountToId());
                    accountTo.setBalance(accountTo.getBalance().add(pendingTransfer.getAmount()));
                    //accountService.updateAccount(userAccount);
                    accountService.updateCurrentUserAccount(userAccount);
                    accountService.updateAccount(accountTo);
                    consoleService.printTransferSuccessMessage(userAccount);
                } else if (Objects.equals(decision, "2")) {
                    pendingTransfer.setStatusId(3);
                    consoleService.printRejectMessage(userAccount);
                } else if(!Objects.equals(decision, "0")){
                    consoleService.printInvalidInputMessage();
                }
                transactionService.update(pendingTransfer);
            } else {
                consoleService.printTransactionNotFoundMessage();
            }
        }
	}

	private void sendBucks() {
        int userId = currentUser.getUser().getId().intValue();
        Account userAccount = accountService.getCurrentUserAccount();
        BigDecimal userBalance = userAccount.getBalance();
        consoleService.printAllUsersButMe(userService.getUsers());
        int sendId = consoleService.promptForSendingId();
        if(sendId != 0) {
            if(sendId == userId) {
                consoleService.printErrorIdMessage();
            } else {
                Account sendAccount = accountService.getAccount(sendId);
                if (sendAccount == null) {
                    consoleService.printInvalidInputMessage();
                } else {
                    BigDecimal senderBalance = sendAccount.getBalance();
                    BigDecimal amount = consoleService.promptForTransferAmount();
                    if (userAccount.checkBalance(amount)) {
                        userBalance = userBalance.subtract(amount);
                        senderBalance = senderBalance.add(amount);
                        userAccount.setBalance(userBalance);
                        sendAccount.setBalance(senderBalance);
                        //accountService.updateAccount(userAccount);
                        accountService.updateCurrentUserAccount(userAccount);
                        accountService.updateAccount(sendAccount);
                        Transfer transfer = new Transfer(2, 2, sendAccount.getAccountId(), userAccount.getAccountId(), amount);
                        transactionService.create(transfer);
                        consoleService.printTransferSuccessMessage(userAccount);
                    } else {
                        consoleService.printNotEnoughBalanceMessage();
                    }
                }
            }
        }

	}

	private void requestBucks() {
        int userId = currentUser.getUser().getId().intValue();
        userAccount = accountService.getAccount(userId);
        consoleService.printAllUsersButMe(userService.getUsers());
        int requestId = consoleService.promptForRequestingId();
        if(requestId != 0) {
            if (requestId == userId) {
                consoleService.printErrorIdMessage();
            } else {
                Account requestAccount = accountService.getAccount(requestId);
                if (requestAccount == null) {
                    consoleService.printInvalidInputMessage();
                } else {
                    BigDecimal amount = consoleService.promptForTransferAmount();
                    if (requestAccount.checkBalance(amount)) {
                        Transfer transfer = new Transfer(1, 1, userAccount.getAccountId(), requestAccount.getAccountId(), amount);
                        transactionService.create(transfer);
                        consoleService.printRequestSuccessMessage();
                    } else {
                        consoleService.printNotEnoughBalanceMessage();
                    }
                }
            }
        }
    }
}
