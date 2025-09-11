package com.api.twitchbets.infrastructure.persistence.memory;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.Wager;
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

    @Test
    void givenAddedBets_whenGetBetsByUsername_thenReturnsBetsForUsername() {
        String request_username = "user1";
        String random_username = "user2";
        Bet bet1 = new Bet(UUID.randomUUID(), request_username, 100, LocalDateTime.now(), 0);
        Bet bet2 = new Bet(UUID.randomUUID(), random_username, 200, LocalDateTime.now(), 0);
        BetOption betOption1 = new BetOption(UUID.randomUUID(), "Yes", List.of(bet1, bet2), 0);
        BetQuestion betQuestion1 = new BetQuestion(UUID.randomUUID(), "question1", List.of(betOption1), LocalDateTime.now());
        List<BetQuestion> betQuestions = List.of(betQuestion1);
        InMemoryBetQuestionRepository betQuestionRepository = new InMemoryBetQuestionRepository(betQuestions);
        List<Bet> expectedBets = List.of(bet1);

        List<Bet> userBets = betQuestionRepository.getBetsByUsername(request_username);

        assertEquals(expectedBets, userBets);
    }

    @Test
    void givenLimit_whenGetLatestWagersByUsername_thenReturnsCorrectAmountOfWagers() {
        String request_username = "user1";
        Bet bet1 = new Bet(UUID.randomUUID(), request_username, 100, LocalDateTime.now().plusMinutes(1), 0);
        Bet bet2 = new Bet(UUID.randomUUID(), request_username, 200, LocalDateTime.now().plusMinutes(2), 0);
        Bet bet3 = new Bet(UUID.randomUUID(), request_username, 300, LocalDateTime.now().plusMinutes(3), 0);
        BetOption betOption1 = new BetOption(UUID.randomUUID(), "Yes", List.of(bet1, bet2, bet3), 0);
        BetQuestion betQuestion1 = new BetQuestion(UUID.randomUUID(), "question1", List.of(betOption1), LocalDateTime.now());

        InMemoryBetQuestionRepository betQuestionRepository = new InMemoryBetQuestionRepository(List.of(betQuestion1));

        List<Wager> latestWagers = betQuestionRepository.getLatestWagersByUsername(request_username, 2);

        assertEquals(2, latestWagers.size());
        assertEquals(bet3, latestWagers.get(0).getBet());
        assertEquals(bet2, latestWagers.get(1).getBet());
    }

    @Test
    void givenLimitGreaterThanPlacedBets_whenGetLatestWagersByUsername_thenReturnsAllWagers() {
        String request_username = "user1";
        Bet bet1 = new Bet(UUID.randomUUID(), request_username, 100, LocalDateTime.now().plusMinutes(1), 0);
        Bet bet2 = new Bet(UUID.randomUUID(), request_username, 200, LocalDateTime.now().plusMinutes(2), 0);
        BetOption betOption1 = new BetOption(UUID.randomUUID(), "Yes", List.of(bet1, bet2), 0);
        BetQuestion betQuestion1 = new BetQuestion(UUID.randomUUID(), "question1", List.of(betOption1), LocalDateTime.now());

        InMemoryBetQuestionRepository betQuestionRepository = new InMemoryBetQuestionRepository(List.of(betQuestion1));

        List<Wager> latestWagers = betQuestionRepository.getLatestWagersByUsername(request_username, 5);

        assertEquals(2, latestWagers.size());
        assertEquals(bet2, latestWagers.get(0).getBet());
        assertEquals(bet1, latestWagers.get(1).getBet());
    }
}