package com.api.twitchbets.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.player.PlayerRepository;

@Service
public class PlayerDetailsService implements UserDetailsService {

    PlayerRepository playerRepository;

    @Autowired
    public PlayerDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User player = playerRepository.getPlayer(username);

            return player;
        }
        catch(Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
