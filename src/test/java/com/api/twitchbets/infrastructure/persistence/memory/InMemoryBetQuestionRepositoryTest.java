package com.api.twitchbets.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.BetQuestion;

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
}