package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public Transfer getById(int id) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if (result.next()) {
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    @Override
    public List<Transfer> list() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            transfers.add(mapRowToTransfer(result));
        }
        return transfers;
    }

    @Override
    public List<Transfer> getByAccountId(int id) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE account_to = ? OR account_from = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id, id);
        while (result.next()) {
            transfers.add(mapRowToTransfer(result));
        }
        return transfers;
    }

    @Override
    public void update(Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getStatusId(), transfer.getId());
    }

    public List<Transfer> getPending(int id) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_status_id = 1 AND account_from = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        while (result.next()) {
            transfers.add(mapRowToTransfer(result));
        }
        return transfers;
    }

//    @Override
//    public List<Transfer> getByAccountFromId(int id) {
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE account_from = ?";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
//        while (result.next()) {
//            transfers.add(mapRowToTransfer(result));
//        }
//        return transfers;
//    }

    @Override
    public void create(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_to, account_from, amount) VALUES(?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, transfer.getTypeId(),transfer.getStatusId(), transfer.getAccountToId(), transfer.getAccountFromId(), transfer.getAmount());
    }

    @Override
    public String accountToName(int id) {
        String name = null;
        String sql = "SELECT username FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id JOIN transfer ON transfer.account_to = account.account_id WHERE account_to = ? Group By username";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if (result.next()) {
            name = result.getString("username");
        }
        return name;
    }

    @Override
    public String accountFromName(int id) {
        String name = null;
        String sql = "SELECT username FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id JOIN transfer ON transfer.account_from = account.account_id WHERE account_from = ? Group By username";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if (result.next()) {
            name = result.getString("username");
        }
        return name;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        int accountToId = rs.getInt("account_to");
        int accountFromId = rs.getInt("account_from");
        transfer.setId(rs.getInt("transfer_id"));
        transfer.setTypeId(rs.getInt("transfer_type_id"));
        transfer.setStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFromId(rs.getInt("account_from"));
        transfer.setAccountToId(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        transfer.setAccountFromName(accountFromName(accountFromId));
        transfer.setAccountToName(accountToName(accountToId));
        return transfer;
    }
}
