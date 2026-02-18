package com.api.twitchbets.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.twitchbets.domain.exceptions.PlayerAlreadyExistsException;
import com.api.twitchbets.domain.exceptions.PlayerNotFoundException;
import com.api.twitchbets.domain.player.Player;
import com.api.twitchbets.domain.player.PlayerRepository;

@Repository
public class InMemoryPlayerRepository implements PlayerRepository {

    private final List<Player> players;

    public InMemoryPlayerRepository() {
        players = new ArrayList<>();
    }

    @Override
    public Player getPlayer(String username) throws PlayerNotFoundException {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }

        throw new PlayerNotFoundException(username);
    }

    @Override
    public void addPlayer(Player player) {
        checkIfUserExists(player.getUsername());

        players.add(player);
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void updatePlayer(Player player) {
        for (Player p : players) {
            if (p.getUsername().equals(player.getUsername())) {
                players.remove(p);
                players.add(player);
                return;
            }
        }
    }

    private void checkIfUserExists(String username) throws PlayerAlreadyExistsException {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                throw new PlayerAlreadyExistsException(username);
            }
        }
    }
}
