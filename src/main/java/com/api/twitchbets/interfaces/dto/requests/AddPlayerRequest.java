package com.api.twitchbets.interfaces.dto.requests;

import com.api.twitchbets.interfaces.annotations.FieldsMatch;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match")
public record AddPlayerRequest (

    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric")
    @JsonProperty
    String username,

    @NotBlank(message = "Password cannot be blank")
    @JsonProperty
    String password,

    @NotBlank(message = "Password confirmation cannot be blank")
    @JsonProperty
    String confirmPassword
) {

}
