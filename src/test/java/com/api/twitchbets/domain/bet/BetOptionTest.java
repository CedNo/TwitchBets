package com.api.twitchbets.domain.bet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BetOptionTest {

    @Test
    void givenBetsInOption_whenGetCurrentAmount_thenReturnTotalAmountOfAllBetsInBets() {
        List<Bet> bets = new ArrayList<>();
        bets.add(new Bet(UUID.randomUUID(), "user1", 125f, LocalDateTime.now(), 0));
        bets.add(new Bet(UUID.randomUUID(), "user2", 130f, LocalDateTime.now(), 0));
        bets.add(new Bet(UUID.randomUUID(), "user3", 0.43f, LocalDateTime.now(), 0));
        final String VALID_OPTION = "Yes";
        BetOption betOption = new BetOption(UUID.randomUUID(), VALID_OPTION, bets, 0f);

        float returnedAmount = betOption.getCurrentAmount();

        assertEquals(255.43f, returnedAmount);
    }

    @Test
    void givenNoBetsInOption_whenGetCurrentAmount_thenReturnZero() {
        List<Bet> bets = new ArrayList<>();
        final String VALID_OPTION = "Yes";
        BetOption betOption = new BetOption(UUID.randomUUID(), VALID_OPTION, bets, 0f);

        float returnedAmount = betOption.getCurrentAmount();

        assertEquals(0, returnedAmount);
    }

    @Test
    void whenPlaceBet_thenAddBetToBets() {
        List<Bet> bets = new ArrayList<>();
        final String VALID_OPTION = "Yes";
        BetOption betOption = new BetOption(UUID.randomUUID(), VALID_OPTION, bets, 0f);
        Bet newBet = new Bet(UUID.randomUUID(), "user1", 125f, LocalDateTime.now(), 0);

        betOption.placeBet(newBet);

        assertTrue(betOption.getBets().contains(newBet));
    }

    @Test
    void whenUpdateOdds_thenCalculateOddsBasedOnCurrentAmount() {
        List<Bet> bets = new ArrayList<>();
        bets.add(new Bet(UUID.randomUUID(), "user1", 100f, LocalDateTime.now(), 0));
        bets.add(new Bet(UUID.randomUUID(), "user2", 200f, LocalDateTime.now(), 0));
        final String VALID_OPTION = "Yes";
        BetOption betOption = new BetOption(UUID.randomUUID(), VALID_OPTION, bets, 0f);

        float bettedAmountOfQuestion = 300f;
        betOption.updateOdds(bettedAmountOfQuestion);

        assertEquals(1f, betOption.getOdds());
    }
}