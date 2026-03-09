package com.api.twitchbets.interfaces.dto.requests;

import com.api.twitchbets.interfaces.annotations.FieldsMatch;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match")
public record AddPlayerRequest (

    @Size(max = 20, message = "Username must be at most 20 characters long")
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric")
    @JsonProperty
    String username,

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 64, message = "Password must be at most 64 characters long")
    @NotBlank(message = "Password cannot be blank")
    @JsonProperty
    String password,

    @NotBlank(message = "Password confirmation cannot be blank")
    @JsonProperty
    String confirmPassword
) {

}
