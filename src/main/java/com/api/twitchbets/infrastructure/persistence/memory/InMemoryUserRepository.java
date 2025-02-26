package com.api.twitchbets.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

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
    public User getUser(int id) throws UserNotFoundException {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        throw new UserNotFoundException(id);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
