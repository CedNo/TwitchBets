package com.api.twitchbets.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.twitchbets.domain.factories.UserFactory;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.domain.user.UserAttributesValidator;
import com.api.twitchbets.domain.user.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final UserAttributesValidator userAttributesValidator;

    @Autowired
    public UserService(
        UserRepository userRepository,
        UserFactory userFactory,
        UserAttributesValidator userAttributesValidator
    ) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.userAttributesValidator = userAttributesValidator;
    }

    public void createUser(String username) {
        userAttributesValidator.validate(username);
        User newUser = userFactory.createUser(username);

        userRepository.addUser(newUser);
    }
}
