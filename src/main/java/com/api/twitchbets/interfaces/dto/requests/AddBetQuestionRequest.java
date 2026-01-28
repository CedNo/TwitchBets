package com.api.twitchbets.interfaces.dto.requests;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddBetQuestionRequest(
    @NotBlank(message = "Question cannot be blank")
    @JsonProperty
    String question,

    @NotNull
    @Size(min=2, message = "At least two options are required")
    @JsonProperty
    List<String> options,

    @NotNull
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "End time must be in the future")
    LocalDateTime endTime
) {

}
