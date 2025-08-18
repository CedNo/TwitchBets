package com.api.twitchbets.interfaces.mappers.responses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.interfaces.dto.responses.BetResponse;

@Component
public class BetResponseMapper {

    public BetResponse toResponse(Bet bet) {
        return new BetResponse(bet);
    }

    public List<BetResponse> toResponseList(List<Bet> bets) {
        return bets.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
