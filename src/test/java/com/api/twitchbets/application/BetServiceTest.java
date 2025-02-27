package com.api.twitchbets.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.BetQuestionRepository;
import com.api.twitchbets.domain.factories.BetOptionFactory;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BetServiceTest {

    final String VALID_QUESTION = "Question";
    final List<String> VALID_OPTIONS = new ArrayList<>();

    @Autowired
    private BetService betService;
    @MockitoBean
    private BetQuestionRepository betQuestionRepository;
    @MockitoBean
    private BetQuestionFactory betQuestionFactory;
    @MockitoBean
    private BetOptionFactory betOptionFactory;
    @MockitoBean
    private BetQuestion betQuestion;
    @MockitoBean
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