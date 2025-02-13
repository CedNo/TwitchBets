package com.api.twitchbets.domain;

import java.util.List;
import java.util.UUID;

public class BetOption {

    private UUID id;
    private List<Bet> bets;

    public BetOption(UUID id, List<Bet> bets) {
        this.id = id;
        this.bets = bets;
    }

    public UUID getId() {
        return id;
    }

    public float getCurrentAmount() {
        float totalAmount = 0;

        for(Bet bet : bets) {
            totalAmount += bet.getAmount();
        }

        return totalAmount;
    }
}
