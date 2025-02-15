package com.api.twitchbets.domain.factories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.twitchbets.domain.BetQuestion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetQuestionFactoryTest {

    @Mock
    private BetQuestionFactory betQuestionFactory;

    @Test
    void whenCreateBetQuestion_thenReturnBetQuestion() {
        BetQuestion newQuestion = new BetQuestion();
        when(betQuestionFactory.createBetQuestion()).thenReturn(newQuestion);

        BetQuestion betQuestion = betQuestionFactory.createBetQuestion();

        assertEquals(newQuestion, betQuestion);
    }
}