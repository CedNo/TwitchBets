package com.api.twitchbets.domain.factories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.BetOption;

@Component
public class BetOptionFactory {

    private BetOption createBetOption(String option) {
        return new BetOption(option);
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
