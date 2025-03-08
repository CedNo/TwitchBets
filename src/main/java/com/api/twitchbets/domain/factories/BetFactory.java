package com.api.twitchbets.domain.factories;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.Bet;

@Component
public class BetFactory {

    public Bet createBet(String username, Float amount) {
        return new Bet(UUID.randomUUID(), username, amount);
    }

}
