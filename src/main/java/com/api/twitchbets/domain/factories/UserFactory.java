package com.api.twitchbets.domain.factories;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.user.User;

@Component
public class UserFactory {

    public static final float INITIAL_BALANCE = 1000;

    public User createUser(String username) {
        return new User(username, INITIAL_BALANCE);
    }

}
