package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private int userId;
    private BigDecimal balance;

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean checkBalance(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) {
            return false;
        } else return amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}
