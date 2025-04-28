package com.grandcapital.transactor.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateEmailRequest {
    @NotBlank(message = "Old email is required")
    @Email(message = "New email must be a valid email address")
    private String oldEmail;
    @NotBlank(message = "Old email is required")
    @Email(message = "New email must be a valid email address")
    private String newEmail;

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
