package com.grandcapital.transactor.exception;

public class PhoneAlreadyExistsException extends RuntimeException {

    public PhoneAlreadyExistsException(Long userId, String phone) {
        super(String.format("Impossible to set phone for user with id %d. Email %s already in use", userId, phone));
    }

}
