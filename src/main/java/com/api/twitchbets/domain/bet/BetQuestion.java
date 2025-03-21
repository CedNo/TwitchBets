package com.api.twitchbets.domain.bet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.api.twitchbets.domain.exceptions.BetOptionNotFoundException;

public class BetQuestion {

    private final UUID id;
    private final String question;
    private final List<BetOption> options;

    public BetQuestion(UUID id, String question, List<BetOption> options) {
        this.id = id;
        this.question = question;
        this.options = options;
    }

    public UUID getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<BetOption> getOptions() {
        return options;
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

    public boolean hasOption(UUID optionId) {
        for (BetOption option : options) {
            if (option.getId().equals(optionId)) {
                return true;
            }
        }

        return false;
    }

    public void placeBet(UUID optionId, Bet bet) throws BetOptionNotFoundException {
        for (BetOption option : options) {
            if (option.getId().equals(optionId)) {
                option.placeBet(bet);
                return;
            }
        }

        throw new BetOptionNotFoundException(optionId);
    }
}
