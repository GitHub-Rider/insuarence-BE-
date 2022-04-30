package com.example.api.dto;

import java.util.List;

public class ComplaintDTO {

    private String complaintId;

    private String location;
    private String description;
    private String date;
    private String time;

    private String customerVehicleRegNo;
    private String customerName;

    private String thirdPartyVehicleRegNo;
    private String thirdPartyFirstName;
    private String thirdPartyLastName;
    private String thirdPartyContactNo;
    private String thirdPartyNic;

    private List<String> images;
    private String recording;

    private String message;

    public ComplaintDTO() {
    }

    public ComplaintDTO(String location, String description, String date, String thirdPartyVehicleRegNo, String thirdPartyFirstName, String thirdPartyLastName, String thirdPartyContactNo, String thirdPartyNic, List<String> images, String recording) {
        this.location = location;
        this.description = description;
        this.date = date;
        this.thirdPartyVehicleRegNo = thirdPartyVehicleRegNo;
        this.thirdPartyFirstName = thirdPartyFirstName;
        this.thirdPartyLastName = thirdPartyLastName;
        this.thirdPartyContactNo = thirdPartyContactNo;
        this.thirdPartyNic = thirdPartyNic;
        this.images = images;
        this.recording = recording;
    }

    // Constructor for the email
    public ComplaintDTO(String complaintId, String location, String date, String time, String customerVehicleRegNo, String customerName) {
        this.complaintId = complaintId;
        this.location = location;
        this.date = date;
        this.time = time;
        this.customerVehicleRegNo = customerVehicleRegNo;
        this.customerName = customerName;
    }

    // Constructor for the police message email
    public ComplaintDTO(String customerName, String message) {
        this.customerName = customerName;
        this.message = message;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThirdPartyVehicleRegNo() {
        return thirdPartyVehicleRegNo;
    }

    public void setThirdPartyVehicleRegNo(String thirdPartyVehicleRegNo) {
        this.thirdPartyVehicleRegNo = thirdPartyVehicleRegNo;
    }

    public String getThirdPartyFirstName() {
        return thirdPartyFirstName;
    }

    public void setThirdPartyFirstName(String thirdPartyFirstName) {
        this.thirdPartyFirstName = thirdPartyFirstName;
    }

    public String getThirdPartyLastName() {
        return thirdPartyLastName;
    }

    public void setThirdPartyLastName(String thirdPartyLastName) {
        this.thirdPartyLastName = thirdPartyLastName;
    }

    public String getThirdPartyContactNo() {
        return thirdPartyContactNo;
    }

    public void setThirdPartyContactNo(String thirdPartyContactNo) {
        this.thirdPartyContactNo = thirdPartyContactNo;
    }

    public String getThirdPartyNic() {
        return thirdPartyNic;
    }

    public void setThirdPartyNic(String thirdPartyNic) {
        this.thirdPartyNic = thirdPartyNic;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerVehicleRegNo() {
        return customerVehicleRegNo;
    }

    public void setCustomerVehicleRegNo(String customerVehicleRegNo) {
        this.customerVehicleRegNo = customerVehicleRegNo;
    }
}
