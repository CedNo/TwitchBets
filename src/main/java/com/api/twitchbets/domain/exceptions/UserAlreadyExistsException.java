package com.api.twitchbets.domain.exceptions;

public class UserAlreadyExistsException extends GenericException {

    public UserAlreadyExistsException(String username) {
        super("USER_ALREADY_EXISTS", String.format("User %s already exists", username));
    }
}
