package com.api.twitchbets.domain.factories;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.user.User;

@Component
public class UserFactory {

    public User createUser(int id, String username) {
        return new User(id, username);
    }

}
