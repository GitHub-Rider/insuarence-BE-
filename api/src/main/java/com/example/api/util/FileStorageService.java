package com.example.api.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.example.api.dto.Response;
import org.apache.commons.io.FileUtils;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileStorageService {

    // Save the image
    public Response save(List<String> data, String vehicleRegNo, String date) {

        try {
            int count = 1;

            ArrayList<String> imageUrls = new ArrayList<>();

            for(String image : data) {
                String filename = vehicleRegNo + "|" + date + "|image-" + count + ".jpg";
                // Decode the base64 file
                byte[] imageByte = Base64.getDecoder().decode(image);

                // Write the bytes to file
                FileUtils.writeByteArrayToFile(new File("uploads/images/" + filename), imageByte);
                // Add the file name to the image urls which will be saved in the database later
                imageUrls.add(filename);

                count++;
            }

            return new Response(1, imageUrls, "Images were saved successfully");

        }

        catch(Exception exception) {
            System.out.println(exception);
            return new Response(0, exception.getMessage());
        }
    }

    // Save the voice recording
    public Response save(String recording, String vehicleRegNo, String date) {

        try {
            String filename = vehicleRegNo + "|" + date + ".m4a";
            // Decode the base64 file
            byte[] imageByte = Base64.getDecoder().decode(recording);

            // Write the bytes to a file
            FileUtils.writeByteArrayToFile(new File("uploads/recordings/" + filename), imageByte);

            return new Response(1, filename, "Recording was saved successfully");

        }

        catch(Exception exception) {
            System.out.println(exception);
            return new Response(0, exception.getMessage());
        }
    }

    public ResponseEntity<byte[]> getImage(String filename) {

        try {
            byte[] image = FileUtils.readFileToByteArray(new File("uploads/images/" + filename));
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        }

        catch(FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
            return ResponseEntity.notFound().build();
        }

        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<byte[]> getRecording(String filename) {

        try {
            byte[] recording = FileUtils.readFileToByteArray(new File("uploads/recordings/" + filename));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(recording);
        }

        catch(FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
            return ResponseEntity.notFound().build();
        }

        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
