package com.api.twitchbets.domain.bet;

import java.util.List;
import java.util.UUID;

public class BetOption {

    private final UUID id;
    private final String option;
    private final List<Bet> bets;

    public BetOption(UUID id, String option, List<Bet> bets) {
        this.id = id;
        this.option = option;
        this.bets = bets;
    }

    public UUID getId() {
        return id;
    }

    public String getOption() {
        return option;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public float getCurrentAmount() {
        float totalAmount = 0;

        for(Bet bet : bets) {
            totalAmount += bet.getAmount();
        }

        return totalAmount;
    }

    public void placeBet(Bet bet) {
        bets.add(bet);
    }
}
