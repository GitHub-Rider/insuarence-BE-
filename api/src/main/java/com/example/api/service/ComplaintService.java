package com.example.api.service;

import com.example.api.dto.ComplaintDTO;
import com.example.api.dto.Response;
import com.example.api.model.Complaint;
import com.example.api.model.ThirdParty;
import com.example.api.model.User;
import com.example.api.repository.ComplaintRepo;
import com.example.api.repository.ThirdPartyRepo;
import com.example.api.util.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepo complaintRepository;

    @Autowired
    private ThirdPartyRepo thirdPartyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EmailService emailService;

    public Response saveComplaint(ComplaintDTO complaint) {

        try {
            User user = userService.getUser();
            ThirdParty thirdParty = new ThirdParty(
                    complaint.getThirdPartyVehicleRegNo(),
                    complaint.getThirdPartyFirstName(),
                    complaint.getThirdPartyLastName(),
                    complaint.getThirdPartyContactNo(),
                    complaint.getThirdPartyNic());

            Complaint complaintDetails = new Complaint(
                    complaint.getLocation(),
                    complaint.getDescription(),
                    user,
                    thirdParty
            );

            String complaintNo = generateComplaintNumber();
            complaintDetails.setComplaintNo(complaintNo);

            // Save the images
            Response imageSave = fileStorageService.save(complaint.getImages(), user.getVehicleRegNo(), String.valueOf(complaintDetails.getDate()));
            Response recordingSave = null;
            int recordingStatus = 1;

            // Save the recording if the file is included
            if(complaint.getRecording() != null) {
                recordingSave = fileStorageService.save(complaint.getRecording(), user.getVehicleRegNo(), String.valueOf(complaintDetails.getDate()));
                recordingStatus = recordingSave.getStatus();
            }

            // Image and recording was successfully saved
            if(imageSave.getStatus() == 1 && recordingStatus == 1) {

                // Save the image urls in the database
                String imageUrls =  imageSave.getData().toString();
                complaintDetails.setImageUrl(imageUrls.substring(1, imageUrls.length() - 1)); // Remove the []

                // Save the recording url in the database
                if(recordingSave != null) complaintDetails.setRecordingUrl((String) recordingSave.getData());

                thirdPartyRepository.save(thirdParty);
                complaintRepository.save(complaintDetails);

                ComplaintDTO complaintDTO = new ComplaintDTO(
                        complaintDetails.getComplaintNo(),
                        complaintDetails.getLocation(),
                        String.valueOf(complaintDetails.getDate()),
                        complaintDetails.getTime(),
                        user.getVehicleRegNo(),
                        user.getFirstName()
                );

                String emailContent = emailService.getNewComplaintEmail(complaintDTO);
                Response response = emailService.sendMail("insurancetest09@gmail.com", "New Complaint", emailContent);

                if(response.getStatus() == 1) {
                    return new Response(1, complaintDetails.getComplaintNo(), "The complaint was successfully recorded");
                }

                else {
                    return response;
                }
            }

            // Recording save has failed
            else if (recordingStatus == 0) {
                return recordingSave;
            }

            // Image save has failed
            else{
                return imageSave;
            }

        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    // View all the complaints by all the users
    public Response viewComplaints() {

        try {
            List<Complaint> complaints = complaintRepository.findAllByOrderByDateDesc();
            return new Response(1, complaints, "Successfully retrieved all the complaints");
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    // View the complaints of the user who's currently logged in (Accident History)
    public Response viewComplaintHistory() {

        try {
            // Retrieve the user
            User user = userService.getUser();
            return new Response(1, user.getComplaints(), "Successfully retrieved the complaint history");
        }

        catch(Exception exception) {
            return new Response(exception);
        }
    }

    // Generate a unique complaint number.
    private String generateComplaintNumber() {

        try {
            String complaintNumber = UUID.randomUUID().toString().split("-")[4].substring(0, 4);
            Optional<Complaint> complaint = complaintRepository.findById(complaintNumber);

            // Re-run the code if the generated complaint number is not unique and exists in the database
            if(complaint.isPresent()) return generateComplaintNumber();
            else return complaintNumber;
        }

        catch(Exception exception) {
            return "null";
        }
    }

}
