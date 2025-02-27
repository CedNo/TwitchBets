package com.api.twitchbets.domain.exceptions;

public class InvalidUsernameException extends GenericException {

    public InvalidUsernameException() {
        super("INVALID_USERNAME", "Invalid username");
    }
}
