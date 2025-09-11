package com.api.twitchbets.domain.bet;

import java.time.LocalDateTime;

public class Wager {

    private final BetQuestion betQuestion;
    private final BetOption betOption;
    private final Bet bet;

    public Wager(BetQuestion betQuestion, BetOption betOption, Bet bet) {
        this.betQuestion = betQuestion;
        this.betOption = betOption;
        this.bet = bet;
    }

    public BetQuestion getBetQuestion() {
        return betQuestion;
    }

    public BetOption getBetOption() {
        return betOption;
    }

    public Bet getBet() {
        return bet;
    }

    public LocalDateTime getCreatedAt() {
        return bet.getCreatedAt();
    }

}
