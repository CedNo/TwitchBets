package com.api.twitchbets.interfaces.dto.responses;

import com.api.twitchbets.domain.user.Player;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerResponse {

    @JsonProperty
    public final String username;
    @JsonProperty
    public final float balance;

    public PlayerResponse(Player player) {
        this.username = player.getUsername();
        this.balance = player.getBalance();
    }

}
