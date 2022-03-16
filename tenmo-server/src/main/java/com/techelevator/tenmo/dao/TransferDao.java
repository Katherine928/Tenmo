package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    Transfer getById(int id);
    List<Transfer> list();
    List<Transfer> getByAccountId(int id);
    //List<Transfer> getByAccountFromId(int id);
    //void create(int transferTypeId, int accountToId, int accountFromId, BigDecimal amount);
    void update(Transfer transfer);
    void create(Transfer transfer);
    String accountToName(int id);
    String accountFromName(int id);
    List<Transfer> getPending(int id);
}
