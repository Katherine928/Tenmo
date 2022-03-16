package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDaO{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            accounts.add(mapRowToAccount(result));
        }
        return accounts;
    }

    @Override
    public Account findByUserId(int id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()) {
            account =  mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public Account findByAccountId(int id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()) {
            account =  mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public Account findByUsername(String username) {
        Account account = null;
        String sql = "SELECT account_id, tenmo_user.user_id, balance " +
                "FROM account " +
                "Join tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE username = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
        if(result.next()) {
            account =  mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public Account updateAccountByUserId(Account account, int id) {
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, account.getBalance(), account.getUserId());
        return account;
    }

    @Override
    public Account updateAccountByUsername(Account account, String username) {
        String sql = "UPDATE account " +
                "SET balance = ? " +
                "WHERE user_id = (Select user_id from tenmo_user Where username = ?)";
        jdbcTemplate.update(sql, account.getBalance(), username);
        return account;
    }

    @Override
    public BigDecimal getBalanceByUserId(int id) {
        Account account = findByAccountId(id);
        return account.getBalance();
    }


    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
