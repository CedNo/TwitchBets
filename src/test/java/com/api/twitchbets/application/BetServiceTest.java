package com.api.twitchbets.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.twitchbets.domain.BetOption;
import com.api.twitchbets.domain.BetQuestion;
import com.api.twitchbets.domain.BetQuestionRepository;
import com.api.twitchbets.domain.factories.BetOptionFactory;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetServiceTest {

    final String VALID_QUESTION = "Question";
    final List<String> VALID_OPTIONS = new ArrayList<>();
    @InjectMocks
    private BetService betService;
    @Mock
    private BetQuestionRepository betQuestionRepository;
    @Mock
    private BetQuestionFactory betQuestionFactory;
    @Mock
    private BetOptionFactory betOptionFactory;
    @Mock
    private BetQuestion betQuestion;
    @Mock
    private List<BetOption> betOptions;

    @Test
    void whenCreateBetQuestion_thenCreateAndSaveNewBetQuestion() {
        when(betOptionFactory.createBetOptions(VALID_OPTIONS)).thenReturn(betOptions);
        when(betQuestionFactory.createBetQuestion(VALID_QUESTION, betOptions)).thenReturn(betQuestion);

        betService.createBetQuestion(VALID_QUESTION, VALID_OPTIONS);

        InOrder inOrder = inOrder(betOptionFactory, betQuestionFactory, betQuestionRepository);
        inOrder.verify(betOptionFactory).createBetOptions(VALID_OPTIONS);
        inOrder.verify(betQuestionFactory).createBetQuestion(VALID_QUESTION, betOptions);
        inOrder.verify(betQuestionRepository).addBetQuestion(betQuestion);
    }

    @Test
    void whenGetBetQuestion_thenGetBetQuestionFromRepository() {
        UUID VALID_ID = UUID.randomUUID();

        betService.getBetQuestion(VALID_ID);

        verify(betQuestionRepository).getBetQuestion(VALID_ID);
    }
}