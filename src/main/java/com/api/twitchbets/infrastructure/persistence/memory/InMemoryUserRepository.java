package com.api.twitchbets.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.twitchbets.domain.exceptions.UserAlreadyExistsException;
import com.api.twitchbets.domain.exceptions.UserNotFoundException;
import com.api.twitchbets.domain.user.User;
import com.api.twitchbets.domain.user.UserRepository;

@Repository
public class InMemoryUserRepository  implements UserRepository {

    private final List<User> users;

    public InMemoryUserRepository() {
        users = new ArrayList<>();
    }

    @Override
    public User getUser(String username) throws UserNotFoundException {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        throw new UserNotFoundException(username);
    }

    @Override
    public void addUser(User user) {
        checkIfUserExists(user.getUsername());

        users.add(user);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    private void checkIfUserExists(String username) throws UserAlreadyExistsException {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                throw new UserAlreadyExistsException(username);
            }
        }
    }
}
