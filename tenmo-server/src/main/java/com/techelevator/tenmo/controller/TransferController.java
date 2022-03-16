package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferDao dao;

//    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
//    public List<Transfer> list() {
//        return dao.list();
//    }

    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer getById(@PathVariable int id) {
        return dao.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void create(@RequestBody Transfer transfer) {
        dao.create(transfer);
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getByAccountId(@PathVariable int id) {
        return dao.getByAccountId(id);
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.PUT)
    public void update(@RequestBody Transfer transfer) {
        dao.update(transfer);
    }

    @RequestMapping(path = "/transfers/pending/{id}", method = RequestMethod.GET)
    public List<Transfer> getPending(@PathVariable int id) {
        return dao.getPending(id);
    }

}
