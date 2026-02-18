package com.api.twitchbets.domain.player;

import java.util.List;

public interface PlayerRepository {

    Player getPlayer(String username);

    void addPlayer(Player player);

    List<Player> getPlayers();

    void updatePlayer(Player player);
}
