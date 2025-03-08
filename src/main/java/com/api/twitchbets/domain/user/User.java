package com.api.twitchbets.domain.user;

public class User {

    private final String username;
    private float balance;

    public User(String username, float balance) {
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public float getBalance() {
        return balance;
    }

    public void charge(float amount) {
        balance -= amount;
    }
}
