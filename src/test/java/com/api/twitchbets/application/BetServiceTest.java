package com.api.twitchbets.application;

import java.time.LocalDateTime;
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

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.BetQuestionRepository;
import com.api.twitchbets.domain.exceptions.BetOptionNotFoundException;
import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;
import com.api.twitchbets.domain.factories.BetFactory;
import com.api.twitchbets.domain.factories.BetOptionFactory;
import com.api.twitchbets.domain.factories.BetQuestionFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BetServiceTest {

    final String VALID_QUESTION = "Question";
    final List<String> VALID_OPTIONS = new ArrayList<>();
    final LocalDateTime VALID_END_TIME = LocalDateTime.now();

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
    @MockitoBean
    private BetFactory betFactory;

    @Test
    void whenCreateBetQuestion_thenCreateAndSaveNewBetQuestion() {
        when(betOptionFactory.createBetOptions(VALID_OPTIONS)).thenReturn(betOptions);
        when(betQuestionFactory.createBetQuestion(VALID_QUESTION, betOptions, VALID_END_TIME)).thenReturn(betQuestion);

        betService.createBetQuestion(VALID_QUESTION, VALID_OPTIONS, VALID_END_TIME);

        InOrder inOrder = inOrder(betOptionFactory, betQuestionFactory, betQuestionRepository);
        inOrder.verify(betOptionFactory).createBetOptions(VALID_OPTIONS);
        inOrder.verify(betQuestionFactory).createBetQuestion(VALID_QUESTION, betOptions, VALID_END_TIME);
        inOrder.verify(betQuestionRepository).addBetQuestion(betQuestion);
    }

    @Test
    void whenGetBetQuestion_thenGetBetQuestionFromRepository() {
        UUID VALID_ID = UUID.randomUUID();

        betService.getBetQuestion(VALID_ID);

        verify(betQuestionRepository).getBetQuestion(VALID_ID);
    }

    @Test
    void whenCreateBet_thenCreateAndSaveUpdatedBetQuestion() {
        Bet VALID_BET = mock();
        when(betFactory.createBet(anyString(), anyFloat())).thenReturn(VALID_BET);
        when(betQuestionRepository.getBetQuestion(any())).thenReturn(betQuestion);

        betService.createBet(anyString(), anyFloat(), UUID.randomUUID(), UUID.randomUUID());

        InOrder inOrder = inOrder(betQuestionRepository, betQuestion, betFactory);
        inOrder.verify(betFactory).createBet(anyString(), anyFloat());
        inOrder.verify(betQuestionRepository).getBetQuestion(any());
        inOrder.verify(betQuestion).placeBet(any(), any());
        inOrder.verify(betQuestionRepository).updateBetQuestion(betQuestion);
    }

    @Test
    void givenValidBetQuestionIdAndBetOptionId_whenCheckIfCanPlaceBet_thenDoNothing() {
        UUID VALID_BET_QUESTION_ID = UUID.randomUUID();
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        when(betQuestionRepository.getBetQuestion(VALID_BET_QUESTION_ID)).thenReturn(betQuestion);
        when(betQuestion.hasOption(VALID_BET_OPTION_ID)).thenReturn(true);

        betService.checkIfCanPlaceBet(VALID_BET_QUESTION_ID, VALID_BET_OPTION_ID);

        verify(betQuestionRepository).getBetQuestion(VALID_BET_QUESTION_ID);
        verify(betQuestion).hasOption(VALID_BET_OPTION_ID);
    }

    @Test
    void givenInvalidBetQuestionId_whenCheckIfCanPlaceBet_thenThrowBetQuestionNotFoundException() {
        UUID INVALID_BET_QUESTION_ID = UUID.randomUUID();
        UUID VALID_BET_OPTION_ID = UUID.randomUUID();
        when(betQuestionRepository.getBetQuestion(INVALID_BET_QUESTION_ID)).thenThrow(new BetQuestionNotFoundException(INVALID_BET_QUESTION_ID));

        assertThrows(
            BetQuestionNotFoundException.class, () ->
            betService.checkIfCanPlaceBet(INVALID_BET_QUESTION_ID, VALID_BET_OPTION_ID)
        );

        verify(betQuestionRepository).getBetQuestion(INVALID_BET_QUESTION_ID);
    }

    @Test
    void givenInvalidBetOptionId_whenCheckIfCanPlaceBet_thenThrowBetOptionNotFoundException() {
        UUID VALID_BET_QUESTION_ID = UUID.randomUUID();
        UUID INVALID_BET_OPTION_ID = UUID.randomUUID();
        when(betQuestionRepository.getBetQuestion(VALID_BET_QUESTION_ID)).thenReturn(betQuestion);
        when(betQuestion.hasOption(INVALID_BET_OPTION_ID)).thenReturn(false);

        assertThrows(
            BetOptionNotFoundException.class, () ->
            betService.checkIfCanPlaceBet(VALID_BET_QUESTION_ID, INVALID_BET_OPTION_ID)
        );

        verify(betQuestionRepository).getBetQuestion(VALID_BET_QUESTION_ID);
        verify(betQuestion).hasOption(INVALID_BET_OPTION_ID);
    }
}