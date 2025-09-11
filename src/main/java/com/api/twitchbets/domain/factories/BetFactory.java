package com.api.twitchbets.domain.factories;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.Bet;

@Component
public class BetFactory {

    private final Clock clock;

    public BetFactory(Clock clock) {
        this.clock = clock;
    }

    public Bet createBet(String username, Float amount) {
        LocalDateTime createdAt = LocalDateTime.now(clock);
        return new Bet(UUID.randomUUID(), username, amount, createdAt, 0);
    }
}
