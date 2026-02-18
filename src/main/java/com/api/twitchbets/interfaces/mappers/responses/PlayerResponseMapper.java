package com.api.twitchbets.interfaces.mappers.responses;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.player.Player;
import com.api.twitchbets.interfaces.dto.responses.PlayerResponse;

@Component
public class PlayerResponseMapper {

    public PlayerResponse toResponse(Player player) {
        return new PlayerResponse(player);
    }
}
