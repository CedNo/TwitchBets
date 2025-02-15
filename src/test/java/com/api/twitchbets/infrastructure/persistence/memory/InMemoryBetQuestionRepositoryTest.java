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
    public void initializeEmptyRepository() {
        repository = new InMemoryBetQuestionRepository();
    }

    @Test
    public void givenNewRepository_whenGetBetQuestions_thenReturnEmptyList() {
        assertTrue(repository.getBetQuestions().isEmpty());
    }

    @Test
    public void givenEmptyRepository_whenAddBetQuestion_thenRepositoryOnlyContainsNewBetQuestion() {
        BetQuestion betquestion = mock();
        List<BetQuestion> expectedList = new ArrayList<>();
        expectedList.add(betquestion);

        repository.addBetQuestion(betquestion);
        List<BetQuestion> returnedList = repository.getBetQuestions();

        assertEquals(expectedList, returnedList);
    }

    @Test
    public void givenBetQuestionsAdded_whenGetBetQuestions_thenContainsAllAddedBetQuestions() {
        BetQuestion betquestion1 = mock();
        BetQuestion betquestion2 = mock();
        repository.addBetQuestion(betquestion1);
        repository.addBetQuestion(betquestion2);

        List<BetQuestion> returnedList = repository.getBetQuestions();

        assertTrue(returnedList.contains(betquestion1));
        assertTrue(returnedList.contains(betquestion2));
    }
}