package com.api.twitchbets.interfaces.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlayerLoginRequest(
    @JsonProperty
    String username,

    @JsonProperty
    String password
) {
}
