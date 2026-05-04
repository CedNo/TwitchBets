package com.api.twitchbets.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.api.twitchbets.application.utilities.CustomPasswordEncoder;
import com.api.twitchbets.domain.factories.PlayerFactory;
import com.api.twitchbets.domain.player.Player;
import com.api.twitchbets.domain.player.PlayerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerFactory playerFactory;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final SessionRegistry sessionRegistry;

    @Autowired
    public PlayerService(
        PlayerRepository playerRepository,
        PlayerFactory playerFactory,
        CustomPasswordEncoder customPasswordEncoder,
        AuthenticationManager authenticationManager,
        SecurityContextRepository securityContextRepository,
        SecurityContextHolderStrategy securityContextHolderStrategy,
        SessionRegistry sessionRegistry

    ) {
        this.playerRepository = playerRepository;
        this.playerFactory = playerFactory;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.securityContextHolderStrategy = securityContextHolderStrategy;
        this.sessionRegistry = sessionRegistry;
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

    public void loginPlayer(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        saveSecurityContext(authenticationResponse, request, response);
    }

    private void saveSecurityContext(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        sessionRegistry.registerNewSession(request.getSession().getId(), request.getSession().getMaxInactiveInterval());
    }

    public boolean checkSession(String sessionId) {
        SessionInformation info = sessionRegistry.getSessionInformation(sessionId);
        return info != null && !info.isExpired();
    }
}
