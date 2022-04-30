package com.example.api.service;

import com.example.api.dto.Response;
import com.example.api.model.User;
import com.example.api.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public Response changePassword(String password) {

        try {
            User user = getUser();
            user.setPassword(bcryptEncoder.encode(password));
            userRepository.save(user);

            return new Response(1, "Changed the password successfully");
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    public Response renewPolicy(String policyType) {

        try {
            User user = getUser();
            user.setInsuranceType(policyType);
            userRepository.save(user);

            return new Response(1, "Successfully renewed the Insurance Policy");
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    // Get the details of the user who is currently logged in
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getById(authentication.getName());
    }

}