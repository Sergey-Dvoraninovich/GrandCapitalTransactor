package com.grandcapital.transactor.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super(String.format("There is no user with id %d", userId));
    }

    public UserNotFoundException(String username) {
        super(String.format("There is no user with username %s", username));
    }

}
