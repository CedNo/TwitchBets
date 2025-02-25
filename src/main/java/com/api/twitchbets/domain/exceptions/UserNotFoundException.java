package com.api.twitchbets.domain.exceptions;

public class UserNotFoundException extends GenericException {

    public UserNotFoundException(int id) {
        super("USER_NOT_FOUND", String.format("User %s not found", id));
    }

}
