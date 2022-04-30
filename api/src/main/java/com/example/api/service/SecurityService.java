package com.example.api.service;

import com.example.api.config.JwtTokenUtil;
import com.example.api.dto.LoginDTO;
import com.example.api.dto.LoginTokenDTO;
import com.example.api.dto.Response;
import com.example.api.model.Admin;
import com.example.api.model.Police;
import com.example.api.model.User;
import com.example.api.repository.AdminRepo;
import com.example.api.repository.PoliceRepo;
import com.example.api.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    UserRepo userRepository;

    @Autowired
    AdminRepo adminRepository;

    @Autowired
    PoliceRepo policeRepository;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Response login(LoginDTO authenticationRequest) {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());

            LoginTokenDTO token = new LoginTokenDTO(
                    jwtTokenUtil.generateToken(userDetails),
                    userDetails.getAuthorities().toString()
            );

            return new Response(1, token, "Login successful");
        }

        catch (Exception exception) {
            if (exception.getMessage().equals("INVALID_CREDENTIALS")) return new Response(0, "Invalid Credentials");
            else return new Response(exception);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findById(username);
        Optional<Admin> admin = adminRepository.findById(username);
        Optional<Police> police = policeRepository.findById(username);

        if (user.isEmpty() && admin.isEmpty() && police.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        else {
            Collection<SimpleGrantedAuthority> roles;

            if (admin.isPresent()) {
                roles = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new org.springframework.security.core.userdetails.User(admin.get().getUsername(), admin.get().getPassword(), roles);
            }

            else if (police.isPresent()) {
                roles = List.of(new SimpleGrantedAuthority("ROLE_POLICE"));
                return new org.springframework.security.core.userdetails.User(police.get().getUsername(), police.get().getPassword(), roles);
            }

            else {
                roles = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                return new org.springframework.security.core.userdetails.User(user.get().getVehicleRegNo(), user.get().getPassword(), roles);
            }
        }

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }

        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
