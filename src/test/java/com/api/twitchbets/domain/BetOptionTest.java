package com.api.twitchbets.domain;

import java.time.LocalDateTime;
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
        bets.add(new Bet(UUID.randomUUID(), "user1", 125f, LocalDateTime.now()));
        bets.add(new Bet(UUID.randomUUID(), "user2", 130f, LocalDateTime.now()));
        bets.add(new Bet(UUID.randomUUID(), "user3", 0.43f, LocalDateTime.now()));
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

    @Test
    void whenPlaceBet_thenAddBetToBets() {
        List<Bet> bets = new ArrayList<>();
        final String VALID_OPTION = "Yes";
        BetOption betOption = new BetOption(UUID.randomUUID(), VALID_OPTION, bets);
        Bet newBet = new Bet(UUID.randomUUID(), "user1", 125f, LocalDateTime.now());

        betOption.placeBet(newBet);

        assertTrue(betOption.getBets().contains(newBet));
    }
}