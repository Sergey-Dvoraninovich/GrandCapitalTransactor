package com.grandcapital.transactor.controller.model;

public class LoginResponse {
    private String token;

    private long expiresIn;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
