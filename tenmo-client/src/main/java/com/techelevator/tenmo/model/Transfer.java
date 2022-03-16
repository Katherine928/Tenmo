package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int id;
    private int typeId;
    private int statusId;
    private int accountToId;
    private int accountFromId;
    private String accountToName;
    private String accountFromName;
    private BigDecimal amount;

    public Transfer(int typeId, int statusId, int accountToId, int accountFromId, BigDecimal amount) {
        this.typeId = typeId;
        this.statusId = statusId;
        this.accountToId = accountToId;
        this.accountFromId = accountFromId;
        this.amount = amount;
    }

    public Transfer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getAccountToName() {
        return accountToName;
    }

    public void setAccountToName(String accountToName) {
        this.accountToName = accountToName;
    }

    public String getAccountFromName() {
        return accountFromName;
    }

    public void setAccountFromName(String accountFromName) {
        this.accountFromName = accountFromName;
    }
}
