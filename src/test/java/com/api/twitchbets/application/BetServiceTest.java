package com.api.twitchbets.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.twitchbets.domain.BetQuestion;
import com.api.twitchbets.domain.BetQuestionRepository;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetServiceTest {

    @InjectMocks
    private BetService betService;
    @Mock
    private BetQuestionRepository betQuestionRepository;
    @Mock
    private BetQuestionFactory betQuestionFactory;
    @Mock
    private BetQuestion betQuestion;

    @Test
    void whenCreateBetQuestion_thenCreateAndSaveNewBetQuestion() {
        when(betQuestionFactory.createBetQuestion()).thenReturn(betQuestion);

        betService.createBetQuestion();

        InOrder inOrder = inOrder(betQuestionFactory, betQuestionRepository);
        inOrder.verify(betQuestionFactory).createBetQuestion();
        inOrder.verify(betQuestionRepository).addBetQuestion(betQuestion);
    }
}