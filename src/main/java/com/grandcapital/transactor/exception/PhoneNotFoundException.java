package com.grandcapital.transactor.exception;

public class PhoneNotFoundException extends RuntimeException {

    public PhoneNotFoundException(Long userId, String phone) {
        super(String.format("Impossible to find phone for user with id %d. Phone %s is not present", userId, phone));
    }

}
