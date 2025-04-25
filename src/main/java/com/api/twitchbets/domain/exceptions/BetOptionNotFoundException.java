package com.api.twitchbets.domain.exceptions;

import java.util.UUID;

public class BetOptionNotFoundException extends NotFoundException {

    public BetOptionNotFoundException(UUID id) {
        super("BET_OPTION_NOT_FOUND", String.format("Bet Option %s not found", id));
    }
}
