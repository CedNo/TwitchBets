package com.api.twitchbets.domain.exceptions;

public class UserNotFoundException extends GenericException {

    public UserNotFoundException(String username) {
        super("USER_NOT_FOUND", String.format("User %s not found", username));
    }

}
