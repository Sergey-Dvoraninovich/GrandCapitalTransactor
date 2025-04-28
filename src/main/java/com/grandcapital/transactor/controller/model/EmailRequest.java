package com.grandcapital.transactor.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class EmailRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
