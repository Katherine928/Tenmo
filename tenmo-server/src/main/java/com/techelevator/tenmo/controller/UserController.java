package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao dao;

    public UserController(UserDao dao) {
        this.dao = dao;
    }


//    @RequestMapping(path = "/user", method = RequestMethod.GET)
//    public List<User> list() {
//        return dao.findAll();
//    }


    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public void create(@PathVariable String name, @PathVariable String password) {
        dao.create(name, password);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<User> getAllButMe(Principal principal) {
        return dao.findAllButMe(principal.getName());
    }


}
