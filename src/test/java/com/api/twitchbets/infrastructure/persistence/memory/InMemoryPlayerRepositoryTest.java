package com.api.twitchbets.infrastructure.persistence.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.exceptions.PlayerAlreadyExistsException;
import com.api.twitchbets.domain.exceptions.PlayerNotFoundException;
import com.api.twitchbets.domain.user.Player;
import com.api.twitchbets.domain.user.PlayerRepository;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryPlayerRepositoryTest {

    private static final String VALID_USERNAME = "test";
    PlayerRepository inMemoryPlayerRepository;
    Player player;

    @BeforeEach
    void setUpRepositoryAndUser() {
        inMemoryPlayerRepository = new InMemoryPlayerRepository();
        player = new Player(VALID_USERNAME, 1000);
    }

    @Test
    void givenNewRepository_whenGetUsers_thenReturnEmptyList() {
        assertTrue(inMemoryPlayerRepository.getPlayers().isEmpty());
    }

    @Test
    void whenAddUser_thenUserIsAdded() {

        inMemoryPlayerRepository.addPlayer(player);

        assertEquals(1, inMemoryPlayerRepository.getPlayers().size());
        assertEquals(player, inMemoryPlayerRepository.getPlayers().get(0));
    }

    @Test
    void givenUserAdded_whenGetUser_thenUserIsReturned() {
        InMemoryPlayerRepository inMemoryUserRepository = new InMemoryPlayerRepository();
        inMemoryUserRepository.addPlayer(player);

        Player returnedUser = inMemoryUserRepository.getPlayer(VALID_USERNAME);

        assertEquals(player, returnedUser);
    }

    @Test
    void givenNoUserAdded_whenGetUser_thenUserNotFoundExceptionIsThrown() {
        assertThrows(PlayerNotFoundException.class, () -> inMemoryPlayerRepository.getPlayer(VALID_USERNAME));
    }

    @Test
    void whenGetUsers_thenAllUsersAreReturned() {
        Player user2 = new Player("test2", 1000);
        inMemoryPlayerRepository.addPlayer(player);
        inMemoryPlayerRepository.addPlayer(user2);

        assertEquals(2, inMemoryPlayerRepository.getPlayers().size());
        assertTrue(inMemoryPlayerRepository.getPlayers().contains(player));
        assertTrue(inMemoryPlayerRepository.getPlayers().contains(user2));
    }

    @Test
    void givenUserAdded_whenAddUserWithAddedUsername_thenThrowUserAlreadyExistsException() {
        inMemoryPlayerRepository.addPlayer(player);

        assertThrows(PlayerAlreadyExistsException.class, () -> inMemoryPlayerRepository.addPlayer(player));
    }

    @Test
    void givenUserAdded_whenUpdateUser_thenUserIsUpdated() {
        inMemoryPlayerRepository.addPlayer(player);
        Player updatedUser = new Player(VALID_USERNAME, 2000);

        inMemoryPlayerRepository.updatePlayer(updatedUser);

        assertEquals(2000, inMemoryPlayerRepository.getPlayer(VALID_USERNAME).getBalance());
    }
}