package com.api.twitchbets.domain.exceptions;

public class PlayerAlreadyExistsException extends GenericException {

    public PlayerAlreadyExistsException(String username) {
        super("PLAYER_ALREADY_EXISTS", String.format("Player %s already exists", username));
    }
}
