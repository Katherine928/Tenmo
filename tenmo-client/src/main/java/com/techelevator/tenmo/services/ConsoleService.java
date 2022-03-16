package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import io.cucumber.java.bs.I;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
    public void printInvalidInputMessage() {
        System.out.println("Invalid Input..");
    }
    public void printErrorIdMessage() {
        System.out.println("You cannot make transactions with yourself...");
    }

    public void printNotEnoughBalanceMessage() {
        System.out.println("Sorry! There is not enough money to make the transaction!");
    }

    public void printTransactionNotFoundMessage() {
        System.out.println("Sorry! There is no available transaction to view, please check your information or come back later... ");
    }
    public void printBalanceMessage(Account account) {
        System.out.println("Your current account balance is : $" + account.getBalance());
    }

    public void printPastTransfer(Transfer[] transfers, Account account) {
        System.out.println("---------------------------------------------------");
        System.out.println("Transfers");
        String formatBanner = String.format("%-20s %-20s %-20s", "ID", "From/To", "Amount");
        System.out.println(formatBanner);
        System.out.println("---------------------------------------------------");
        for (Transfer transfer: transfers) {
            if (transfer.getAccountFromId() == account.getAccountId()) {
                String formatMessage = String.format("%-20s %-20s $%-20s", transfer.getId(),"To: " + transfer.getAccountToName(), transfer.getAmount());
                System.out.println(formatMessage);
            } else {
                String formatMessage = String.format("%-20s %-20s $%-20s", transfer.getId(),"From: " + transfer.getAccountFromName(), transfer.getAmount());
                System.out.println(formatMessage);
            }
        }
        System.out.println("-----------");

    }
    public void printPendingTransfer(Transfer[] transfers) {
        System.out.println("---------------------------------------------------");
        System.out.println("Pending Transfers");
        String formatBanner = String.format("%-20s %-20s %-20s", "ID", "To", "Amount");
        System.out.println(formatBanner);
        System.out.println("---------------------------------------------------");
        for (Transfer transfer: transfers) {
            String formatMessage = String.format("%-20s %-20s $%-20s", transfer.getId(),transfer.getAccountToName(), transfer.getAmount());
            System.out.println(formatMessage);

        }
        System.out.println("-----------");
    }

    public void printTransferSuccessMessage(Account account) {
        System.out.println("\nMoney has been successfully transferred. Your current balance is: $" + account.getBalance() + "\n");
    }

    public void printRequestSuccessMessage() {
        System.out.println("\nMoney request has been successfully created. Status: Pending. Please wait to be approved.\n");
    }

    public void printRejectMessage(Account account) {
        System.out.println("\nYou successfully reject the money request. Your balance will remain the same. Balance: $" + account.getBalance() + '\n');
    }

    public int promptForTransferId() {
        System.out.println("Please enter transfer ID to view details (0 to cancel): ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void printTransferDetails(Transfer transfer, Account account) {
        System.out.println("------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("------------------------------------------");
        System.out.println("Id: " + transfer.getId());
        if (transfer.getAccountToId() == account.getAccountId()) {
            System.out.println("From: " + transfer.getAccountFromName());
            System.out.println("To: Me");
        } else {
            System.out.println("From: Me");
            System.out.println("To: " + transfer.getAccountToName());
        }
        if(transfer.getTypeId() == 1) {
            System.out.println("Type: Request");
        } else {
            System.out.println("Type: Send");
        }
        if (transfer.getStatusId() == 1) {
            System.out.println("Status: Pending");
        } else if (transfer.getStatusId() == 2) {
            System.out.println("Status: Approved");
        } else {
            System.out.println("Status: Rejected");
        }
        System.out.println("Amount: $" + transfer.getAmount());
    }


    public void printAllUsersButMe(User[] users) {
        System.out.println("-----------------------------------------------");
        System.out.println("Users");
        String bannerFormat = String.format("%-20s %-20s", "ID", "Name");
        System.out.println(bannerFormat);
        System.out.println("-----------------------------------------------");
        for (User user: users) {
            String MessageFormat = String.format("%-20s %-20s", user.getId(), user.getUsername());
            System.out.println(MessageFormat);
        }
        System.out.println("-----------\n");
    }

    public int promptForSendingId() {
        System.out.println("Enter ID of user you are sending to (0 to cancel):");
        int id = Integer.parseInt(scanner.next());
        return id;
    }

    public int promptForRequestingId() {
        System.out.println("Enter ID of user you are requesting from (0 to cancel):");
        int id = Integer.parseInt(scanner.nextLine());
        return id;
    }

    public BigDecimal promptForTransferAmount() {
        System.out.println("Enter amount:");
        BigDecimal amount = new BigDecimal(scanner.next());
        return amount;
    }

    public int promptForPendingId() {
        System.out.println("Please enter transfer ID to approve/reject (0 to cancel):");
        return Integer.parseInt(scanner.nextLine());
    }

    public String printPendingTransferDecision() {
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        System.out.println("--------");
        System.out.println("Please choose an option:");
        return scanner.nextLine();
    }
}
