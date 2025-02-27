package com.api.twitchbets.domain.user;

import java.util.List;

public interface UserRepository {

    User getUser(String username);

    void addUser(User user);

    List<User> getUsers();
}
