package com.api.twitchbets.domain.bet;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bet {

    private final UUID id;
    private final String username;
    private final float amount;
    private final LocalDateTime createdAt;

    public Bet(UUID id, String username, float amount, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public float getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
