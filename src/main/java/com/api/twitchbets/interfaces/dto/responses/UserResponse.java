package com.api.twitchbets.interfaces.dto.responses;

import com.api.twitchbets.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

    @JsonProperty
    public final String username;
    @JsonProperty
    public final float balance;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.balance = user.getBalance();
    }

}
