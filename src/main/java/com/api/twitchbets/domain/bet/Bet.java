package com.api.twitchbets.domain.bet;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bet {

    private final UUID id;
    private final String username;
    private final float amount;
    private final LocalDateTime createdAt;
    private final float betWin;

    public Bet(UUID id, String username, float amount, LocalDateTime createdAt, float betWin) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.createdAt = createdAt;
        this.betWin = betWin;
    }

    public float getAmount() {
        return amount;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {
        return id;
    }

    public float getBetWin() {
        return betWin;
    }

}
