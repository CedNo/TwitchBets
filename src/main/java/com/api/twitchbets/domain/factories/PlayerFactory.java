package com.api.twitchbets.domain.factories;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.player.Player;

@Component
public class PlayerFactory {

    public static final float INITIAL_BALANCE = 1000;

    public Player createNormalPlayer(String username, String password) {
        return new Player(username, password, INITIAL_BALANCE);
    }

}
