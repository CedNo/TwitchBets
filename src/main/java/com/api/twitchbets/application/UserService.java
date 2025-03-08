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

    public User getUser(String username) {
        User user = userRepository.getUser(username);

        return user;
    }

    public void checkIfCanPlaceBet(String username, float amount) {
        User user = userRepository.getUser(username);
        user.checkIfCanPlaceBet(amount);
    }

    public void chargeUser(String username, float amount) {
        User user = userRepository.getUser(username);
        user.charge(amount);
        userRepository.updateUser(user);
    }
}
