package com.api.twitchbets.domain.factories;

import org.springframework.stereotype.Component;

import com.api.twitchbets.domain.BetOption;

@Component
public class BetOptionFactory {

    public BetOption createBetOption() {
        return new BetOption();
    }
}
