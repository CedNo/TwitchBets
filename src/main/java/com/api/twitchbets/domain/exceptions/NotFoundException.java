package com.api.twitchbets.domain.exceptions;

public class NotFoundException extends GenericException {

    public NotFoundException(String error, String description) {
        super(error, description);
    }
}
