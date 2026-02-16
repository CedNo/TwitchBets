package com.api.twitchbets.domain.factories;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.user.Player;

@Component
public class PlayerFactory {

    public static final float INITIAL_BALANCE = 1000;

    public Player createPlayer(String username) {
        return new Player(username, INITIAL_BALANCE);
    }

}
