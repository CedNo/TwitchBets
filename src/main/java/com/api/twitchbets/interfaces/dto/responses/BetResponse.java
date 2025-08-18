package com.api.twitchbets.interfaces.dto.responses;

import java.time.LocalDateTime;

import com.api.twitchbets.domain.bet.Bet;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BetResponse {

    @JsonProperty
    public final String username;
    @JsonProperty
    public final float amount;
    @JsonProperty
    public final LocalDateTime createdAt;

    public BetResponse(Bet bet) {
        this.username = bet.getUsername();
        this.amount = bet.getAmount();
        this.createdAt = bet.getCreatedAt();
    }
}
