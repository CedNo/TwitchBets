package com.api.twitchbets.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.factories.PlayerFactory;
import com.api.twitchbets.domain.user.Player;
import com.api.twitchbets.domain.user.PlayerAttributesValidator;
import com.api.twitchbets.domain.user.PlayerRepository;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerFactory playerFactory;
    private final PlayerAttributesValidator playerAttributesValidator;

    @Autowired
    public PlayerService(
        PlayerRepository playerRepository,
        PlayerFactory playerFactory,
        PlayerAttributesValidator playerAttributesValidator
    ) {
        this.playerRepository = playerRepository;
        this.playerFactory = playerFactory;
        this.playerAttributesValidator = playerAttributesValidator;
    }

    public void createPlayer(String username) {
        playerAttributesValidator.validate(username);
        Player newPlayer = playerFactory.createPlayer(username);

        playerRepository.addPlayer(newPlayer);
    }

    public Player getPlayer(String username) {
        Player player = playerRepository.getPlayer(username);

        return player;
    }

    public void checkIfCanPlaceBet(String username, float amount) {
        Player player = playerRepository.getPlayer(username);
        player.checkIfCanPlaceBet(amount);
    }

    public void chargePlayer(String username, float amount) {
        Player player = playerRepository.getPlayer(username);
        player.charge(amount);
        playerRepository.updatePlayer(player);
    }
}
