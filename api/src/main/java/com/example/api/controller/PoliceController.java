package com.example.api.controller;

import com.example.api.dto.ComplaintDTO;
import com.example.api.dto.LoginDTO;
import com.example.api.dto.Response;
import com.example.api.service.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/police")
public class PoliceController {

    @Autowired
    PoliceService policeService;

    @GetMapping("/login")
    public Response login(@RequestBody LoginDTO loginDTO) {
        return policeService.login(loginDTO);
    }

    @GetMapping("/get")
    public Response getComplaints() {
        return policeService.getComplaints();
    }

    @PostMapping("/send")
    public Response sendMessage(@RequestBody ComplaintDTO complaintDTO) {
        return policeService.sendMessage(complaintDTO);
    }
}
