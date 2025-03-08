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
        checkIfCanPlaceBet(amount);

        balance -= amount;
        //todo: test
    }

    public void checkIfCanPlaceBet(float amount) throws IllegalArgumentException {
        if(balance < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        } else if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }
}
