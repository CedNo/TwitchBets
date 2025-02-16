package com.api.twitchbets.application;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.twitchbets.domain.BetOption;
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
        final String VALID_QUESTION = "Question";
        final List<BetOption> VALID_OPTIONS = new ArrayList<>();
        when(betQuestionFactory.createBetQuestion(VALID_QUESTION, VALID_OPTIONS)).thenReturn(betQuestion);

        betService.createBetQuestion(VALID_QUESTION, VALID_OPTIONS);

        InOrder inOrder = inOrder(betQuestionFactory, betQuestionRepository);
        inOrder.verify(betQuestionFactory).createBetQuestion(VALID_QUESTION, VALID_OPTIONS);
        inOrder.verify(betQuestionRepository).addBetQuestion(betQuestion);
    }
}