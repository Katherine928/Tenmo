package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class Account {

    private int accountId;
    private int userId;
    @DecimalMin(value = "1.0", message = "The field should be greater than 0.")
    private BigDecimal balance;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
