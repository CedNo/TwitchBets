package com.api.twitchbets.domain.exceptions;

public class PlayerNotFoundException extends NotFoundException {

    public PlayerNotFoundException(String username) {
        super("PLAYER_NOT_FOUND", String.format("Player %s not found", username));
    }

}
