package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final String BASE_SERVICE_URL;
    public String AUTH_TOKEN = null;
    private RestTemplate restTemplate = new RestTemplate();

    public UserService(String baseUrl) {
        this.BASE_SERVICE_URL = baseUrl;
    }

    public void setAUTH_TOKEN(String AUTH_TOKEN) {
        this.AUTH_TOKEN = AUTH_TOKEN;
    }

    public User[] getUsers() {
        User[] users = null;
        ResponseEntity<User[]> response = restTemplate.exchange(BASE_SERVICE_URL + "/user", HttpMethod.GET, makeAuthEntity(), User[].class);
        users = response.getBody();
        return users;
    }

    private HttpEntity<User> makeUserEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        return new HttpEntity<>(user, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        return new HttpEntity<>(headers);
    }
}
