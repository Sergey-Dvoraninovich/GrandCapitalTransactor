package com.grandcapital.transactor.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdatePhoneRequest {

    @NotBlank(message = "Old phone is required")
    @Pattern(regexp = "^\\+\\d{12}$", message = "Phone number must start with '+' followed by 12 digits")
    private String oldPhone;

    @NotBlank(message = "New phone is required")
    @Pattern(regexp = "^\\+\\d{12}$", message = "Phone number must start with '+' followed by 12 digits")
    private String newPhone;

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }
    
}
