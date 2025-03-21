package com.api.twitchbets.domain.bet;

import java.util.UUID;

public class Bet {

    private final UUID id;
    private final String username;
    private final float amount;

    public Bet(UUID id, String username, float amount) {
        this.id = id;
        this.username = username;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

}
