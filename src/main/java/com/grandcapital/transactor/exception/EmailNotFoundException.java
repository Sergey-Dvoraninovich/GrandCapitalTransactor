package com.grandcapital.transactor.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(Long userId, String email) {
        super(String.format("Impossible to find email for user with id %d. Email %s is not present", userId, email));
    }

}
