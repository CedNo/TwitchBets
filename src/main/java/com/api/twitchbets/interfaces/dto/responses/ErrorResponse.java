package com.api.twitchbets.interfaces.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(@JsonProperty String error, @JsonProperty String description) {}
