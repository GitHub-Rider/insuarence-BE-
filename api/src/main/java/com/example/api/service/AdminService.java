package com.example.api.service;

import com.example.api.dto.Response;
import com.example.api.model.Admin;
import com.example.api.model.Complaint;
import com.example.api.model.User;
import com.example.api.repository.AdminRepo;
import com.example.api.repository.ComplaintRepo;
import com.example.api.repository.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ComplaintRepo complaintRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public Response register(User user) {

        try {
            Optional<User> existingUser = userRepository.findById(user.getVehicleRegNo());

            // Check if a user with the same vehicle registration number exists
            if (existingUser.isEmpty()) {
                user.setPassword(bcryptEncoder.encode(user.getVehicleRegNo())); // Password will be changed later by the user

                // Validate fields
                Response validationResponse = validate(user);

                // If the fields were validated successfully, save the user
                if(validationResponse.getStatus() == 1) {
                    userRepository.save(user);
                    return new Response(1, "User was successfully added");
                }

                else {
                    return validationResponse;
                }
            }

            else {
                return new Response(0, "User exists");
            }

        }

        catch (Exception exception) {
            return new Response(exception);
        }
    }

    // Get all the users in the database
    public Response getAll() {

        try {
            List<User> users = userRepository.findAll();
            return new Response(1, users, "Successfully retrieved all users");
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    public Response deleteUser(String vehicleRegNo) {

        try {
            Optional<User> user = userRepository.findById(vehicleRegNo);

            // Check if a user with the requested vehicle registration number exists, and if so delete it
            if(user.isPresent()) {
                userRepository.deleteById(vehicleRegNo);

                return new Response(1, "Successfully deleted the user");
            }

            else {
                return new Response(0, "Couldn't find the user");
            }
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    private Response validate(User user) {

        // Validate the vehicle registration number
        if(!user.getVehicleRegNo().matches("^[a-zA-Z]{2,3}[0-9]{4}$")) {
            System.out.println("Invalid Reg No " + user.getVehicleRegNo());
            return new Response(0, "Enter a valid Vehicle Registration Number");
        }

        // First name
        if(user.getFirstName().matches(".*\\d.*")) {
            return new Response(0, "Enter a valid Name");
        }

        // Last name
        if (user.getLastName().matches(".*\\d.*")) {
            return new Response(0, "Enter a valid Name");
        }

        // contact number
        if (!user.getContactNo().matches("^\\d{10}$")) {
            System.out.println("Invalid contact number " + user.getContactNo());
            return new Response(0, "Enter a valid Contact Number");
        }
        // nic
        if(!user.getNic().matches("^[0-9]{12}$") && !user.getNic().matches("^[0-9]{9}[vV]$")) {
            System.out.println("Invalid NIC " + user.getNic());
            return new Response(0, "Enter a valid NIC");
        }

        // email
        if(!user.getEmail().matches("^(.+)@(.+)$")) {
            System.out.println("Invalid email " + user.getEmail());
            return new Response(0, "Enter a valid Email");
        }

        return new Response(1, "Successfully validated all fields");

    }

    // Send a copy of the complaint to the police
    public Response sendToPolice(String complaintNo) {

        try {
            Optional<Complaint> complaint = complaintRepository.findById(complaintNo);

            if(complaint.isPresent()) {
                complaint.get().setPoliceInvolved(true);
                complaintRepository.save(complaint.get());

                return new Response(1, "A copy of the complaint was sent to the police successfully");
            }

            else {
                return new Response(0, "Complaint not found");
            }
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    // Create a default admin account if it doesn't exist in the database
    @PostConstruct
    private void addAdmin() {

        try {
            Optional<Admin> defaultAdmin = adminRepository.findById("admin");

            if(defaultAdmin.isEmpty()) {
                Admin admin = new Admin("admin", bcryptEncoder.encode("1234"));
                adminRepository.save(admin);

                logger.info("Created default admin");
            }
        }

        catch(Exception exception) {
            logger.error("An error occurred while creating the default admin");
            logger.error(exception.getMessage());
        }
    }

}
