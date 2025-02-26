package com.api.twitchbets.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;

import static org.junit.jupiter.api.Assertions.*;

class BetOptionTest {

    @Test
    void givenBetsInOption_whenGetCurrentAmount_thenReturnTotalAmountOfAllBetsInBets() {
        List<Bet> bets = new ArrayList<>();
        bets.add(new Bet(125f));
        bets.add(new Bet(130f));
        bets.add(new Bet(0.43f));
        final String VALID_OPTION = "Yes";
        BetOption betOption = new BetOption(UUID.randomUUID(), VALID_OPTION, bets);

        float returnedAmount = betOption.getCurrentAmount();

        assertEquals(255.43f, returnedAmount);
    }

    @Test
    void givenNoBetsInOption_whenGetCurrentAmount_thenReturnZero() {
        List<Bet> bets = new ArrayList<>();
        final String VALID_OPTION = "Yes";
        BetOption betOption = new BetOption(UUID.randomUUID(), VALID_OPTION, bets);

        float returnedAmount = betOption.getCurrentAmount();

        assertEquals(0, returnedAmount);
    }
}