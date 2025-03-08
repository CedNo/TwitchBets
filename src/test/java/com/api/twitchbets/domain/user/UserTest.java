package com.api.twitchbets.domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void givenUser_whenCharge_thenReturnUserWithUpdatedBalance() {
        final String VALID_USERNAME = "user1";
        final float VALID_BALANCE = 1000;
        User user = new User(VALID_USERNAME, VALID_BALANCE);

        user.charge(100);

        assertEquals(900, user.getBalance());
    }

}