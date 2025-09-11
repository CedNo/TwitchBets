package com.api.twitchbets.interfaces.dto.responses;

import java.util.List;
import java.util.UUID;

import com.api.twitchbets.domain.bet.BetOption;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BetOptionResponse {

    @JsonProperty
    public final UUID id;
    @JsonProperty
    public final String option;
    @JsonProperty
    public final List<BetResponse> bets;
    @JsonProperty
    public float odds;
    @JsonProperty
    public final float currentAmount;

    public BetOptionResponse(BetOption betOption) {
        this.id = betOption.getId();
        this.option = betOption.getOption();
        this.bets = betOption.getBets().stream()
                .map(BetResponse::new)
                .toList();
        this.odds = betOption.getOdds();
        this.currentAmount = betOption.getCurrentAmount();
    }
}
