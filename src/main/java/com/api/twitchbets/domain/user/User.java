package com.api.twitchbets.domain.user;

public class User {

    private final int id;
    private final String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }
}
