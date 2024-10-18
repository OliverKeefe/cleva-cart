package org.clevacart.entity;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserEntity {
    private String username;
    private String password;
    private String email;
    private int role;
    private int mobileNumber;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return this.role;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getMobileNumber() {
        return this.mobileNumber;
    }

}
