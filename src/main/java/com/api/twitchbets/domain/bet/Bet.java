package com.api.twitchbets.domain.bet;

import java.util.UUID;

import com.api.twitchbets.domain.user.User;

public class Bet {

    private final UUID id;
    private final User user;
    private final float amount;

    public Bet(UUID id, User user, float amount) {
        this.id = id;
        this.user = user;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

}
