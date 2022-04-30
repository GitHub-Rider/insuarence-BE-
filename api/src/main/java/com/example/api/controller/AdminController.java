package com.example.api.controller;

import com.example.api.dto.Response;
import com.example.api.model.User;
import com.example.api.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    // Create a new account
    @PostMapping("/register")
    public Response register(@RequestBody User user) {
        return adminService.register(user);
    }

    // Get all accounts
    @GetMapping("/get")
    public Response getAll() {
        return adminService.getAll();
    }

    // Delete an account
    @DeleteMapping("/delete")
    public Response deleteUser(@RequestBody User user) {
        return adminService.deleteUser(user.getVehicleRegNo());
    }

    // Send a copy of the complaint to the police
    @PutMapping("/send/{complaintId}")
    public Response sendToPolice(@PathVariable String complaintId) {
        return adminService.sendToPolice(complaintId);
    }
}
