package com.api.twitchbets.infrastructure.persistence.memory;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InMemoryBetQuestionRepositoryTest {

    private InMemoryBetQuestionRepository repository;

    @BeforeEach
    void initializeEmptyRepository() {
        repository = new InMemoryBetQuestionRepository();
    }

    @Test
    void givenNewRepository_whenGetBetQuestions_thenReturnEmptyList() {
        assertTrue(repository.getBetQuestions().isEmpty());
    }

    @Test
    void givenEmptyRepository_whenAddBetQuestion_thenRepositoryOnlyContainsNewBetQuestion() {
        BetQuestion betquestion = mock();
        List<BetQuestion> expectedList = new ArrayList<>();
        expectedList.add(betquestion);

        repository.addBetQuestion(betquestion);
        List<BetQuestion> returnedList = repository.getBetQuestions();

        assertEquals(expectedList, returnedList);
    }

    @Test
    void givenBetQuestionsAdded_whenGetBetQuestions_thenContainsAllAddedBetQuestions() {
        BetQuestion betQuestion1 = mock();
        BetQuestion betQuestion2 = mock();
        repository.addBetQuestion(betQuestion1);
        repository.addBetQuestion(betQuestion2);

        List<BetQuestion> returnedList = repository.getBetQuestions();

        assertTrue(returnedList.contains(betQuestion1));
        assertTrue(returnedList.contains(betQuestion2));
    }

    @Test
    void givenInvalidId_whenGetBetQuestion_thenThrowsBetQuestionNotFoundException() {
        UUID INVALID_ID = UUID.randomUUID();

        assertThrows(
            BetQuestionNotFoundException.class,
            () -> repository.getBetQuestion(INVALID_ID)
        );
    }

    @Test
    void givenBetQuestionAdded_whenGetBetQuestion_thenReturnsCorrespondingBetQuestion() {
        UUID id = UUID.randomUUID();
        BetQuestion expectedBetQuestion = new BetQuestion(id, "question", new ArrayList<>(), LocalDateTime.now());
        repository.addBetQuestion(expectedBetQuestion);

        BetQuestion returnedBetQuestion = repository.getBetQuestion(id);

        assertEquals(expectedBetQuestion, returnedBetQuestion);
    }

    @Test
    void givenBetQuestionAdded_whenUpdateBetQuestion_thenRepositoryContainsUpdatedBetQuestion() {
        UUID id = UUID.randomUUID();
        BetQuestion betQuestion = new BetQuestion(id, "question", new ArrayList<>(), LocalDateTime.now());
        repository.addBetQuestion(betQuestion);

        BetQuestion updatedBetQuestion = new BetQuestion(id, "question", new ArrayList<>(), LocalDateTime.now());
        repository.updateBetQuestion(updatedBetQuestion);

        List<BetQuestion> returnedList = repository.getBetQuestions();

        assertEquals(updatedBetQuestion, returnedList.get(0));
    }

    @Test
    void givenAmount_whenGetEndingBetQuestions_thenReturnsListOfEndingBetQuestions() {
        Clock clock = Clock.systemUTC();
        BetQuestion betQuestion1 = new BetQuestion(UUID.randomUUID(), "question1", new ArrayList<>(), LocalDateTime.now(clock).plusMinutes(10));
        BetQuestion betQuestion2 = new BetQuestion(UUID.randomUUID(), "question2", new ArrayList<>(), LocalDateTime.now(clock).plusMinutes(20));
        BetQuestion betQuestion3 = new BetQuestion(UUID.randomUUID(), "question3", new ArrayList<>(), LocalDateTime.now(clock).plusMinutes(30));
        repository.addBetQuestion(betQuestion1);
        repository.addBetQuestion(betQuestion3);
        repository.addBetQuestion(betQuestion2);

        List<BetQuestion> endingBetQuestions = repository.getEndingBetQuestions(2, clock);

        assertEquals(2, endingBetQuestions.size());
        assertEquals(betQuestion1, endingBetQuestions.get(0));
        assertEquals(betQuestion2, endingBetQuestions.get(1));
    }

    @Test
    void givenNegativeAmount_whenGetEndingBetQuestions_thenReturnsEmptyList() {
        Clock clock = Clock.systemUTC();
        BetQuestion betQuestion1 = new BetQuestion(UUID.randomUUID(), "question1", new ArrayList<>(), LocalDateTime.now(clock).plusMinutes(10));
        BetQuestion betQuestion2 = new BetQuestion(UUID.randomUUID(), "question2", new ArrayList<>(), LocalDateTime.now(clock).plusMinutes(20));
        repository.addBetQuestion(betQuestion1);
        repository.addBetQuestion(betQuestion2);

        List<BetQuestion> endingBetQuestions = repository.getEndingBetQuestions(-1, clock);

        assertTrue(endingBetQuestions.isEmpty());
    }
}