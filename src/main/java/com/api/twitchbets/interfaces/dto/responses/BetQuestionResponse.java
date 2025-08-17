package com.api.twitchbets.interfaces.dto.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.api.twitchbets.domain.bet.BetOption;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BetQuestionResponse {
    @JsonProperty
    public final UUID id;
    @JsonProperty
    private final String question;
    @JsonProperty
    private final List<BetOption> options;
    @JsonProperty
    private final LocalDateTime endTime;

    public BetQuestionResponse(
        UUID id,
        String question,
        List<BetOption> options,
        LocalDateTime endTime
    ) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.endTime = endTime;
    }
}
