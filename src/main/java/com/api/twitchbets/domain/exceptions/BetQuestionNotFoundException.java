package com.api.twitchbets.domain.exceptions;

import java.util.UUID;

public class BetQuestionNotFoundException extends GenericException {

    public BetQuestionNotFoundException(UUID id) {
        super("BET_QUESTION_NOT_FOUND", String.format("Bet Question %s not found", id));
    }
}
