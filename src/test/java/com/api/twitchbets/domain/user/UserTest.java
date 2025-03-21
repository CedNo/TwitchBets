package com.api.twitchbets.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setupUser() {
        final String VALID_USERNAME = "user1";
        final float VALID_BALANCE = 1000;
        user = new User(VALID_USERNAME, VALID_BALANCE);
    }

    @Test
    void givenUser_whenCharge_thenReturnUserWithUpdatedBalance() {

        user.charge(100);

        assertEquals(900, user.getBalance());
    }

    @Test
    void givenUserWithInsufficientFunds_whenCharge_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> user.charge(1001));
    }

    @Test
    void givenUserWithNegativeAmount_whenCharge_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> user.charge(-1));
    }

    @Test
    void givenValidAmount_whenCharge_thenBalanceIsUpdated() {

        user.charge(100);

        assertEquals(900, user.getBalance());
    }

    @Test
    void givenInvalidAmount_whenCharge_thenBalanceIsNotUpdated() {

        try {
        user.charge(10000);
        } catch (Exception e) {}

        assertEquals(1000, user.getBalance());
    }

    @Test
    void givenAmountHigherThanBalance_whenCheckIfCanPlaceBet_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> user.checkIfCanPlaceBet(1001));
    }

    @Test
    void givenNegativeAmount_whenCheckIfCanPlaceBet_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> user.checkIfCanPlaceBet(-1));
    }

    @Test
    void givenAmountEqualToZero_whenCheckIfCanPlaceBet_thenThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> user.checkIfCanPlaceBet(0));
    }
}