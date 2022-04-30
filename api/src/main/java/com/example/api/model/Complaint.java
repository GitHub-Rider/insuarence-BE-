package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Complaint implements Serializable {

    @Id
    private String complaintNo;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @Nullable
    private String recordingUrl;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @Column(columnDefinition = "boolean default false")
    private Boolean policeInvolved;

    @Column(length = 3000)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userRegNo")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thirdPartyRegNo")
    private ThirdParty thirdParty;

    @Column(nullable = false, updatable = false)
    private String time;

    public Complaint() {

    }

    public Complaint(String location, String description, User user, ThirdParty thirdParty) {
        this.location = location;
        this.description = description;
        this.user = user;
        this.thirdParty = thirdParty;
        this.policeInvolved = false;
        this.time = LocalTime.now(ZoneId.of("GMT+05:30")).format(DateTimeFormatter.ofPattern("hh:mm a"));
        this.date = LocalDate.now(ZoneId.of("GMT+05:30"));
    }

    public String getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        this.complaintNo = complaintNo;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecordingUrl() {
        return recordingUrl;
    }

    public void setRecordingUrl(String recordingUrl) {
        this.recordingUrl = recordingUrl;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Boolean isPoliceInvolved() {
        return policeInvolved;
    }

    public void setPoliceInvolved(Boolean policeInvolved) {
        this.policeInvolved = policeInvolved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
