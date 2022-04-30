package com.example.api.controller;

import com.example.api.dto.LoginDTO;
import com.example.api.dto.Response;
import com.example.api.model.User;
import com.example.api.service.SecurityService;
import com.example.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    // Login
    @PostMapping("/login")
    public Response login(@RequestBody LoginDTO user) {
        return securityService.login(user);
    }

    // Change password
    @PutMapping("/password")
    public Response changePassword(@RequestBody LoginDTO user) {
        return userService.changePassword(user.getPassword());
    }

    // Change the policy type
    @PutMapping("/renew")
    public Response renewPolicy(@RequestBody User user) {
        return userService.renewPolicy(user.getInsuranceType());
    }

}
