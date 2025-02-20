package com.api.twitchbets.domain.exceptions;

public class GenericException extends RuntimeException {

    public final String error;
    public final String description;

    public GenericException(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
