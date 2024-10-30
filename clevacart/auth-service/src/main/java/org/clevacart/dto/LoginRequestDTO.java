package org.clevacart.dto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginRequestDTO {
    public String username;
    public String password;

    public LoginRequestDTO() {}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


}
