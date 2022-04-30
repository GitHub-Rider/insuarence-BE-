package com.example.api.controller;

import com.example.api.dto.ComplaintDTO;
import com.example.api.dto.Response;
import com.example.api.service.ComplaintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    // File a new complaint
    @PostMapping("/new")
    public Response complain(@RequestBody ComplaintDTO complaint) {
        return complaintService.saveComplaint(complaint);
    }

    // Get current users complaints (Complaint History)
    @GetMapping("/get/current")
    public Response getComplaintHistory() {
        return complaintService.viewComplaintHistory();
    }

    // Admin only
    // Get all complaints
    @GetMapping("/get")
    public Response getAll() {
        return complaintService.viewComplaints();
    }

}
