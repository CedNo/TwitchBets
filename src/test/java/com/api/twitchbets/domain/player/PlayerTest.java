package com.api.twitchbets.domain.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setupPlayer() {
        final String VALID_USERNAME = "player1";
        final float VALID_BALANCE = 1000;
        player = new Player(VALID_USERNAME, VALID_BALANCE);
    }

    @Test
    void givenPlayer_whenCharge_thenReturnPlayerWithUpdatedBalance() {

        player.charge(100);

        assertEquals(900, player.getBalance());
    }

    @Test
    void givenPlayerWithInsufficientFunds_whenCharge_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> player.charge(1001));
    }

    @Test
    void givenPlayerWithNegativeAmount_whenCharge_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> player.charge(-1));
    }

    @Test
    void givenValidAmount_whenCharge_thenBalanceIsUpdated() {

        player.charge(100);

        assertEquals(900, player.getBalance());
    }

    @Test
    void givenInvalidAmount_whenCharge_thenBalanceIsNotUpdated() {

        try {
        player.charge(10000);
        } catch (Exception e) {}

        assertEquals(1000, player.getBalance());
    }

    @Test
    void givenAmountHigherThanBalance_whenCheckIfCanPlaceBet_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> player.checkIfCanPlaceBet(1001));
    }

    @Test
    void givenNegativeAmount_whenCheckIfCanPlaceBet_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> player.checkIfCanPlaceBet(-1));
    }

    @Test
    void givenAmountEqualToZero_whenCheckIfCanPlaceBet_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> player.checkIfCanPlaceBet(0));
    }
}