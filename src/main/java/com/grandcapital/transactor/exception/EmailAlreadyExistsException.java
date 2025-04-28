package com.grandcapital.transactor.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(Long userId, String email) {
        super(String.format("Impossible to set email for user with id %d. Email %s already in use", userId, email));
    }

}
