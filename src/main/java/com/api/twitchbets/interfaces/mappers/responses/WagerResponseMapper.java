package com.api.twitchbets.interfaces.mappers.responses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.Wager;
import com.api.twitchbets.interfaces.dto.responses.WagerResponse;

@Component
public class WagerResponseMapper {

    public WagerResponse toResponse(Wager wager) {
        return new WagerResponse(wager);
    }

    public List<WagerResponse> toResponseList(List<Wager> wagers) {
        return wagers.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
