package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    private String vehicleRegNo;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String contactNo;

    @NotBlank
    private String nic;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String password;

    @NotBlank
    private String policyNo;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @NotBlank
    private String insuranceType;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Complaint> complaints;

    public User() {

    }

    public User(String vehicleRegNo, String firstName, String lastName, String contactNo, String nic, String email, String address, String password, String policyNo, String expiryDate, String insuranceType) {
        this.vehicleRegNo = vehicleRegNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.nic = nic;
        this.email = email;
        this.address = address;
        this.password = password;
        this.policyNo = policyNo;
        this.expiryDate = LocalDate.parse(expiryDate);
        this.insuranceType = insuranceType;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }
}
