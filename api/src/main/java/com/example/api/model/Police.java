package com.example.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Police {

    @Id
    private String username;

    private String password;

    private String location;

    public Police() {
    }

    public Police(String username, String password, String location) {
        this.username = username;
        this.password = password;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String location) {
        this.username = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
