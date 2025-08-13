package com.api.twitchbets.domain.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.bet.BetOption;

@Component
public class BetOptionFactory {

    private BetOption createBetOption(String option) {
        return new BetOption(UUID.randomUUID(), option, new ArrayList<>(), 0f);
    }

    public List<BetOption> createBetOptions(List<String> options) {
        List<BetOption> betOptions = new ArrayList<>();

        for(String option : options) {
            BetOption newBetOption = createBetOption(option);
            betOptions.add(newBetOption);
        }

        return betOptions;
    }
}
