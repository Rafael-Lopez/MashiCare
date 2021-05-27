package com.lopez.rafael.mashicare.controllers;

import com.lopez.rafael.mashicare.dtos.UserDto;
import com.lopez.rafael.mashicare.entities.User;
import com.lopez.rafael.mashicare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @PostMapping(path = "/user")
    public User createNewUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }
}
