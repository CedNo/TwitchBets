package com.api.twitchbets.infrastructure.persistence.memory;

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
        BetQuestion expectedBetQuestion = new BetQuestion(id, "question", new ArrayList<>());
        repository.addBetQuestion(expectedBetQuestion);

        BetQuestion returnedBetQuestion = repository.getBetQuestion(id);

        assertEquals(expectedBetQuestion, returnedBetQuestion);
    }

    @Test
    void givenBetQuestionAdded_whenUpdateBetQuestion_thenRepositoryContainsUpdatedBetQuestion() {
        UUID id = UUID.randomUUID();
        BetQuestion betQuestion = new BetQuestion(id, "question", new ArrayList<>());
        repository.addBetQuestion(betQuestion);

        BetQuestion updatedBetQuestion = new BetQuestion(id, "question", new ArrayList<>());
        repository.updateBetQuestion(updatedBetQuestion);

        List<BetQuestion> returnedList = repository.getBetQuestions();

        assertEquals(updatedBetQuestion, returnedList.get(0));
    }
}