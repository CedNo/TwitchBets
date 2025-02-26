package com.api.twitchbets.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.factories.UserFactory;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.domain.user.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Autowired
    public UserService(
        UserRepository userRepository,
        UserFactory userFactory
    ) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    public void createUser(int id, String username) {
        User newUser = userFactory.createUser(id, username);

        userRepository.addUser(newUser);
    }
}
