package com.grandcapital.transactor.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PhoneRequest {

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+\\d{12}$", message = "Phone number must start with '+' followed by 12 digits")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
