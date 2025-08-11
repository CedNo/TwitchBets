package com.api.twitchbets.interfaces.dto.requests;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddBetQuestionRequest(
    @NotBlank
    @JsonProperty
    String question,

    @NotNull
    @JsonProperty
    List<String> options,

    @NotNull
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime
) {

}
