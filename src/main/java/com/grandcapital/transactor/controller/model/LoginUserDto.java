package com.grandcapital.transactor.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginUserDto {
    @Pattern(regexp = "^\\+\\d{12}$", message = "Phone number must start with '+' followed by 12 digits")
    private String phone;
    @Email(message = "Email must be a valid email address")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must at least 8 characters")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*()]).+",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String password;

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
