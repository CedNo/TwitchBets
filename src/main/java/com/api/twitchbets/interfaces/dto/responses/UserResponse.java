package com.api.twitchbets.interfaces.dto.responses;

import com.api.twitchbets.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

    @JsonProperty
    public User user;

    public UserResponse(User user) {
        this.user = user;
    }

}
