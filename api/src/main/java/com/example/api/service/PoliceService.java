package com.example.api.service;

import com.example.api.dto.ComplaintDTO;
import com.example.api.dto.LoginDTO;
import com.example.api.dto.LoginTokenDTO;
import com.example.api.dto.Response;
import com.example.api.model.Complaint;
import com.example.api.model.Police;
import com.example.api.repository.ComplaintRepo;
import com.example.api.repository.PoliceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class PoliceService {

    private final Logger logger = LoggerFactory.getLogger(PoliceService.class);

    @Autowired
    private PoliceRepo policeRepository;

    @Autowired
    private ComplaintRepo complaintRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private EmailService emailService;

    public Response login(LoginDTO loginDTO) {

        try {
            Response loginDetails = securityService.login(loginDTO);
            LoginTokenDTO data = (LoginTokenDTO) loginDetails.getData();

            if(data.getRole().equals("[ROLE_POLICE]")) {
                return loginDetails;
            }

            else {
                return new Response(0, "Invalid Credentials");
            }
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    public Response getComplaints() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Police> user = policeRepository.findById(authentication.getName());

            List<Complaint> complaints = complaintRepository.findAllByLocationAndPoliceInvolved(user.get().getLocation(), true);

            return new Response(1, complaints, "Successfully retrieved all the complaints");
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    public Response sendMessage(ComplaintDTO complaintDTO) {

        try {
            Optional<Complaint> complaint = complaintRepository.findById(complaintDTO.getComplaintId());

            if(complaint.isPresent()) {
                complaint.get().setMessage(complaintDTO.getMessage());
                complaintRepository.save(complaint.get());

                ComplaintDTO complaintDetails = new ComplaintDTO(
                        complaint.get().getUser().getFirstName(),
                        complaint.get().getMessage()
                );

                String emailContent = emailService.getPoliceMessageEmail(complaintDetails);
                Response response = emailService.sendMail("insurancetest09@gmail.com", "Police Message", emailContent);

                if(response.getStatus() == 1) {
                    return new Response(1, "Message was sent successfully");
                }

                else {
                    return response;
                }

            }

            return new Response(0, "Couldn't find an complaint with that Id");
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    // Create the default police accounts
    @PostConstruct
    private void addDefaultAccount() {

        try {

            Police kolpity = new Police("kolpity", bcryptEncoder.encode("1234"), "kolpity");
            Police mountLavinia = new Police("mtlav", bcryptEncoder.encode("1234"), "mount lavinia");

            policeRepository.save(kolpity);
            policeRepository.save(mountLavinia);

            logger.info("Created default police accounts");

        }

        catch(Exception exception) {
            logger.error("An error occurred while creating the default admin");
            logger.error(exception.getMessage());
        }
    }
}
