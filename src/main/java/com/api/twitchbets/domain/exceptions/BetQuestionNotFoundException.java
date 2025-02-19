package com.api.twitchbets.domain.exceptions;

public class BetQuestionNotFoundException extends GenericException {

    public BetQuestionNotFoundException() {
        super("CHARACTER_NOT_FOUND", "Character not found");
    }
}
