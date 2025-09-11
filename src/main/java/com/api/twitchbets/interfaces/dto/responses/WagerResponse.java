package com.api.twitchbets.interfaces.dto.responses;

import java.time.LocalDateTime;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.Wager;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WagerResponse {

    @JsonProperty
    public final String username;
    @JsonProperty
    public final float amount;
    @JsonProperty
    public final LocalDateTime createdAt;
    @JsonProperty
    public final String betQuestionId;
    @JsonProperty
    public final String betQuestion;
    @JsonProperty
    public final String betOptionId;
    @JsonProperty
    public final String betOption;
    @JsonProperty
    public final float betWin;

    public WagerResponse(Wager wager) {
        Bet bet = wager.getBet();
        BetQuestion betQuestion = wager.getBetQuestion();
        BetOption betOption = wager.getBetOption();

        this.username = bet.getUsername();
        this.amount = bet.getAmount();
        this.createdAt = bet.getCreatedAt();
        this.betQuestionId = betQuestion.getId().toString();
        this.betQuestion = betQuestion.getQuestion();
        this.betOptionId = betOption.getId().toString();
        this.betOption = betOption.getOption();
        this.betWin = bet.getBetWin();
    }

}
