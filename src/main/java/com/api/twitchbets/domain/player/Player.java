package com.api.twitchbets.domain.player;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;

public class Player extends User implements Serializable {

    private float balance;

    public Player(String username, String password, float balance) {
        super(username, password, new ArrayList<>());
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void charge(float amount) {
        checkIfCanPlaceBet(amount);

        balance -= amount;
    }

    public void checkIfCanPlaceBet(float amount) throws IllegalArgumentException {
        if(balance < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        } else if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }
}
