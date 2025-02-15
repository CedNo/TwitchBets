package com.api.twitchbets.domain.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.BetQuestion;

import static org.junit.jupiter.api.Assertions.*;

class BetQuestionFactoryTest {

    private BetQuestionFactory betQuestionFactory;

    @BeforeEach
    void setupBetQuestionFactory() {
        betQuestionFactory = new BetQuestionFactory();
    }

    @Test
    void whenCreateBetQuestion_thenReturnBetQuestion() {

        BetQuestion betQuestion = betQuestionFactory.createBetQuestion();

        assertEquals(0, betQuestion.getCurrentBettedAmount());
    }
}