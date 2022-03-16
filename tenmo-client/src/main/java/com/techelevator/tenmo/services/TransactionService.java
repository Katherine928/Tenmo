package com.techelevator.tenmo.services;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

public class TransactionService {
    private final String BASE_SERVICE_URL;
    public String AUTH_TOKEN = null;
    private RestTemplate restTemplate = new RestTemplate();

    public TransactionService(String baseUrl) {this.BASE_SERVICE_URL = baseUrl; }

    public void setAUTH_TOKEN(String AUTH_TOKEN) { this.AUTH_TOKEN = AUTH_TOKEN; }


    public void create(Transfer transfer) {
        restTemplate.postForObject(BASE_SERVICE_URL + "/user/transfers", makeTransfer(transfer), Transfer.class);
    }


    public Transfer[] get(int id) {
        Transfer[] transfers = null;
        ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_SERVICE_URL + "user/transfers/" + id, HttpMethod.GET, makeAuthEntity(),Transfer[].class);
        transfers = response.getBody();
        return transfers;
    }

    public Transfer[] getPending(int id) {
        Transfer[] transfers = null;
        ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_SERVICE_URL + "user/transfers/pending/" + id, HttpMethod.GET, makeAuthEntity(),Transfer[].class);
        transfers = response.getBody();
        return transfers;
    }

    public Transfer getById(int id){
        Transfer transfer = null;
        ResponseEntity<Transfer> entity = restTemplate.exchange(BASE_SERVICE_URL + "/user/transfer/" + id, HttpMethod.GET, makeAuthEntity(), Transfer.class);
        transfer = entity.getBody();
        return transfer;
    }

    public void update(Transfer transfer) {
        restTemplate.exchange(BASE_SERVICE_URL + "/user/transfers", HttpMethod.PUT, makeTransfer(transfer),Transfer.class);
    }



    private HttpEntity<Transfer> makeTransfer(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer,headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        return new HttpEntity<>(headers);
    }

}
