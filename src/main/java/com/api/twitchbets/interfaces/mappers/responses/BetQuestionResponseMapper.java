package com.api.twitchbets.interfaces.mappers.responses;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.interfaces.dto.responses.BetQuestionResponse;

@Component
public class BetQuestionResponseMapper {

    public BetQuestionResponse toResponse(BetQuestion betQuestion) {
        return new BetQuestionResponse(betQuestion);
    }
}
