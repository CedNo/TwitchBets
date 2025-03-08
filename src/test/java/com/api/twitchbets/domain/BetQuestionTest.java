package com.api.twitchbets.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

import static org.junit.jupiter.api.Assertions.*;

class BetQuestionTest {

    private BetOption firstBetOption;
    private BetOption secondBetOption;
    private BetOption thirdBetOption;

    private final BetQuestionFactory betQuestionFactory = new BetQuestionFactory();
    private BetQuestion betQuestion;

    @BeforeEach
    void setupBetQuestion() {
        String question = "Question?";
        final String VALID_OPTION1 = "Yes";
        List<Bet> bets1 = new ArrayList<>();
        bets1.add(new Bet(UUID.randomUUID(), "user1", 115f));
        firstBetOption = new BetOption(UUID.randomUUID(), VALID_OPTION1, bets1);
        final String VALID_OPTION2 = "No";
        List<Bet> bets2 = new ArrayList<>();
        bets2.add(new Bet(UUID.randomUUID(), "user2", 120f));
        secondBetOption = new BetOption(UUID.randomUUID(), VALID_OPTION2, bets2);
        final String VALID_OPTION3 = "Toaster";
        List<Bet> bets3 = new ArrayList<>();
        bets3.add(new Bet(UUID.randomUUID(), "user3", 0.5f));
        thirdBetOption = new BetOption(UUID.randomUUID(), VALID_OPTION3, bets3);
        List<BetOption> betOptions = new ArrayList<>();
        betOptions.add(firstBetOption);
        betOptions.add(secondBetOption);
        betOptions.add(thirdBetOption);
        betQuestion = betQuestionFactory.createBetQuestion(question, betOptions);
    }

    @Test
    void whenGetCurrentBettedAmount_thenReturnTotalAmountOfAllBets() {

        float returnedAmount = betQuestion.getCurrentBettedAmount();

        assertEquals(235.5f, returnedAmount);
    }

    @Test
    void whenGetCurrentOddsOfOptions_thenReturnMapOfBetOptionsIdAndOdds() {
        Map<UUID, Float> expectedMap = new HashMap<>();
        expectedMap.put(firstBetOption.getId(), 0.4883227176f);
        expectedMap.put(secondBetOption.getId(), 0.5095541401f);
        expectedMap.put(thirdBetOption.getId(), 0.0021231423f);

        Map<UUID, Float> returnedMap = betQuestion.getCurrentOddsOfOptions();

        assertEquals(expectedMap.keySet(), returnedMap.keySet());
        assertEquals(expectedMap.values().stream().toList(), returnedMap.values().stream().toList());
    }

    @Test
    void whenGetCurrentOddsOfOptions_thenTotalOddsShouldBe1() {

        Map<UUID, Float> returnedMap = betQuestion.getCurrentOddsOfOptions();
        float totalOdds = 0;
        for(Float odd : returnedMap.values()) {
            totalOdds += odd;
        }

        assertEquals(1f, totalOdds);
    }
}