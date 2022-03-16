package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


public interface AccountDaO {
    List<Account> findAll();
    Account findByUserId(int id);
    Account findByAccountId(int id);
    Account findByUsername(String username);
    Account updateAccountByUserId(Account account, int id);
    Account updateAccountByUsername(Account account, String username);
    BigDecimal getBalanceByUserId(int id);
}
