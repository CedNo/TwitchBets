package com.api.twitchbets.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.exceptions.BetOptionNotFoundException;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        bets1.add(new Bet(UUID.randomUUID(), "user1", 115f, LocalDateTime.now(), 0));
        firstBetOption = new BetOption(UUID.randomUUID(), VALID_OPTION1, bets1, 0f);
        final String VALID_OPTION2 = "No";
        List<Bet> bets2 = new ArrayList<>();
        bets2.add(new Bet(UUID.randomUUID(), "user2", 120f, LocalDateTime.now(), 0));
        secondBetOption = new BetOption(UUID.randomUUID(), VALID_OPTION2, bets2, 0f);
        final String VALID_OPTION3 = "Toaster";
        List<Bet> bets3 = new ArrayList<>();
        bets3.add(new Bet(UUID.randomUUID(), "user3", 0.5f, LocalDateTime.now(), 0));
        thirdBetOption = new BetOption(UUID.randomUUID(), VALID_OPTION3, bets3, 0f);
        List<BetOption> betOptions = new ArrayList<>();
        LocalDateTime endTime = LocalDateTime.now();
        betOptions.add(firstBetOption);
        betOptions.add(secondBetOption);
        betOptions.add(thirdBetOption);
        betQuestion = betQuestionFactory.createBetQuestion(question, betOptions, endTime);
    }

    @Test
    void whenGetCurrentBettedAmount_thenReturnTotalAmountOfAllBets() {

        float returnedAmount = betQuestion.getCurrentBettedAmount();

        assertEquals(235.5f, returnedAmount);
    }

    @Test
    void givenValidOptionId_whenHasOption_thenReturnTrue() {
        UUID validOptionId = firstBetOption.getId();

        boolean hasOption = betQuestion.hasOption(validOptionId);

        assertTrue(hasOption);
    }

    @Test
    void givenInvalidOptionId_whenHasOption_thenReturnFalse() {
        UUID invalidOptionId = UUID.randomUUID();

        boolean hasOption = betQuestion.hasOption(invalidOptionId);

        assertFalse(hasOption);
    }

    @Test
    void givenValidOptionId_whenPlaceBet_thenPlaceBetAndUpdateOddsOfOptions() {
        BetOption betOption = mock();
        UUID optionId = UUID.randomUUID();
        when(betOption.getId()).thenReturn(optionId);
        BetQuestion betQuestion1 = new BetQuestion(UUID.randomUUID(), "Question?", List.of(betOption), LocalDateTime.now());
        Bet bet = mock();

        betQuestion1.placeBet(optionId, bet);

        verify(betOption).placeBet(bet);
        verify(betOption).updateOdds(betQuestion1.getCurrentBettedAmount());
    }

    @Test
    void givenInvalidOptionId_whenPlaceBet_thenThrowBetOptionNotFoundException() {
        Bet bet = mock();

        assertThrows(BetOptionNotFoundException.class, () -> betQuestion.placeBet(UUID.randomUUID(), bet));
    }
}