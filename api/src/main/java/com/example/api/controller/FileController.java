package com.example.api.controller;

import com.example.api.util.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    // Get image from the database
    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        return fileStorageService.getImage(filename);
    }

    // Get recording from the database
    @GetMapping("/recording/{filename}")
    public ResponseEntity<byte[]> getRecording(@PathVariable("filename") String filename) {
        return fileStorageService.getRecording(filename);
    }

}
