package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import io.cucumber.java.bs.A;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AccountService {
    private final String BASE_SERVICE_URL;
    private String AUTH_TOKEN = null;
    private RestTemplate restTemplate = new RestTemplate();


    public AccountService(String baseUrl) {this.BASE_SERVICE_URL = baseUrl; }


    public Account getAccount(int id){
        Account account = null;
        ResponseEntity<Account> entity =  restTemplate.exchange(BASE_SERVICE_URL + "accounts/" + id, HttpMethod.GET, makeAuthEntity(), Account.class);
        account = entity.getBody();
        return account;
    }

    public Account getCurrentUserAccount() {
        Account account = null;
        ResponseEntity<Account> entity =  restTemplate.exchange(BASE_SERVICE_URL + "accounts/account", HttpMethod.GET, makeAuthEntity(), Account.class);
        account = entity.getBody();
        return account;
    }

    public void updateAccount(Account account) {
        restTemplate.exchange(BASE_SERVICE_URL + "accounts/account", HttpMethod.PUT, makeAccount(account), Account.class);
    }

    public void updateCurrentUserAccount(Account account) {
        restTemplate.exchange(BASE_SERVICE_URL + "accounts", HttpMethod.PUT, makeAccount(account),Account.class);
    }


    public Account getByAccountId(int id) {
        Account account = null;
        ResponseEntity<Account> entity =  restTemplate.exchange(BASE_SERVICE_URL + "accounts/account/" + id, HttpMethod.GET, makeAuthEntity(), Account.class);
        account = entity.getBody();
        return account;
    }


    public void setAUTH_TOKEN(String AUTH_TOKEN) { this.AUTH_TOKEN = AUTH_TOKEN; }

    private HttpEntity<Account> makeAccount(Account account){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Account> entity = new HttpEntity<>(account,headers);
        return entity;
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }



}
