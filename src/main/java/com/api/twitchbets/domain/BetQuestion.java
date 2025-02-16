package com.api.twitchbets.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BetQuestion {

    private String question;
    private final List<BetOption> options;

    public BetQuestion(String question, List<BetOption> options) {
        this.question = question;
        this.options = options;
    }

    public Map<UUID, Float> getCurrentOddsOfOptions() {
        Map<UUID, Float> oddsOfOptions = new HashMap<>();
        float totalBettedAmount = getCurrentBettedAmount();

        for(BetOption option : options) {
            float amount = option.getCurrentAmount();

            float odd = amount / totalBettedAmount;

            oddsOfOptions.put(option.getId(), odd);
        }

        return oddsOfOptions;
    }

    public float getCurrentBettedAmount() {
        float totalAmount = 0;

        for (BetOption option : options) {
            totalAmount += option.getCurrentAmount();
        }

        return totalAmount;
    }
}
