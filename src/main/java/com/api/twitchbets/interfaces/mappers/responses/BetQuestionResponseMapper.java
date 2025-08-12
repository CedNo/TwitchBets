package com.api.twitchbets.interfaces.mappers.responses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.interfaces.dto.responses.BetQuestionResponse;

@Component
public class BetQuestionResponseMapper {

    public BetQuestionResponse toResponse(BetQuestion betQuestion) {
        return new BetQuestionResponse(betQuestion);
    }

    public List<BetQuestionResponse> toResponseList(List<BetQuestion> betQuestions) {
        return betQuestions.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
