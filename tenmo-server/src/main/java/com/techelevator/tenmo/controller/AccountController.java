package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDaO;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {


    @Autowired
    private AccountDaO dao;

//    @RequestMapping(path = "", method = RequestMethod.GET)
//    public List<Account> list() {
//        return dao.findAll();
//    }

    @RequestMapping( path = "/{id}", method = RequestMethod.GET)
    public Account get(@PathVariable int id) {
        return dao.findByUserId(id);
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getCurrentUserAccountByUsername(Principal principal) {
        return dao.findByUsername(principal.getName());
    }

    @RequestMapping(path = "/account", method = RequestMethod.PUT)
    public Account update(@RequestBody Account account) {
        return dao.updateAccountByUserId(account, account.getUserId());
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Account updateCurrentUserAccount(@RequestBody Account account, Principal principal) {
        return dao.updateAccountByUsername(account, principal.getName());
    }


    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public Account getByAccountId(@PathVariable int id) {
        return dao.findByAccountId(id);
    }


}
