package com.api.twitchbets.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.api.twitchbets.application.utilities.CustomPasswordEncoder;
import com.api.twitchbets.domain.factories.PlayerFactory;
import com.api.twitchbets.domain.player.Player;
import com.api.twitchbets.domain.player.PlayerRepository;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerFactory playerFactory;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public PlayerService(
        PlayerRepository playerRepository,
        PlayerFactory playerFactory,
        CustomPasswordEncoder customPasswordEncoder,
        AuthenticationProvider authenticationProvider
    ) {
        this.playerRepository = playerRepository;
        this.playerFactory = playerFactory;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    public void createPlayer(String username, String password) {
        String encodedPassword = this.customPasswordEncoder.encode(password);

        Player newPlayer = playerFactory.createNormalPlayer(username, encodedPassword);

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

    public void loginPlayer(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        if(!authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
}
