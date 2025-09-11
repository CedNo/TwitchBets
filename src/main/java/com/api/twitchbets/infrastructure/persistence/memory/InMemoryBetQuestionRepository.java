package com.api.twitchbets.infrastructure.persistence.memory;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.api.twitchbets.domain.bet.Bet;
import com.api.twitchbets.domain.bet.BetOption;
import com.api.twitchbets.domain.bet.BetQuestion;
import com.api.twitchbets.domain.bet.BetQuestionRepository;
import com.api.twitchbets.domain.bet.Wager;
import com.api.twitchbets.domain.exceptions.BetQuestionNotFoundException;

@Repository
public class InMemoryBetQuestionRepository implements BetQuestionRepository {

    private final List<BetQuestion> betQuestions;

    public InMemoryBetQuestionRepository() {
        betQuestions = new ArrayList<>();
    }

    public InMemoryBetQuestionRepository(List<BetQuestion> betQuestions) {
        this.betQuestions = betQuestions;
    }

    @Override
    public BetQuestion getBetQuestion(UUID id) throws BetQuestionNotFoundException {
        for (BetQuestion betQuestion : betQuestions) {
            if (betQuestion.getId().equals(id)) {
                return betQuestion;
            }
        }

        throw new BetQuestionNotFoundException(id);
    }

    @Override
    public List<BetQuestion> getBetQuestions() {
        return betQuestions;
    }

    @Override
    public void addBetQuestion(BetQuestion betQuestion) {
        betQuestions.add(betQuestion);
    }

    @Override
    public void updateBetQuestion(BetQuestion betQuestion) {
        for (BetQuestion question : betQuestions) {
            if (question.getId().equals(betQuestion.getId())) {
                betQuestions.remove(question);
                betQuestions.add(betQuestion);
                return;
            }
        }
    }

    @Override
    public List<BetQuestion> getEndingBetQuestions(int amount, Clock clock) {
        List<BetQuestion> endingBetQuestions = new ArrayList<>();

        betQuestions.sort(Comparator.comparing(BetQuestion::getEndTime));

        for (BetQuestion betQuestion : betQuestions) {
            if (endingBetQuestions.size() >= amount) {
                break;
            }
            if (betQuestion.getEndTime().isAfter(LocalDateTime.now(clock))) {
                endingBetQuestions.add(betQuestion);
            }
        }

        return endingBetQuestions;
    }

    @Override
    public List<Bet> getBetsByUsername(String username) {
        List<Bet> userBets = new ArrayList<>();

        for (BetQuestion betQuestion : betQuestions) {
            for (BetOption option : betQuestion.getOptions()) {
                for (Bet bet : option.getBets()) {
                    if (bet.getUsername().equals(username)) {
                        userBets.add(bet);
                    }
                }
            }
        }

        return userBets;
    }

    @Override
    public List<Wager> getLatestWagersByUsername(String username, int limit) {
        List<Wager> userWagers = new ArrayList<>();

        for (BetQuestion betQuestion : betQuestions) {
            for (BetOption betOption : betQuestion.getOptions()) {
                for (Bet bet : betOption.getBets()) {
                    if (bet.getUsername().equals(username)) {
                        Wager wager = new Wager(betQuestion, betOption, bet);
                        userWagers.add(wager);
                    }
                }
            }
        }

        userWagers.sort(Comparator.comparing(Wager::getCreatedAt).reversed());
        List<Wager> latestBets = userWagers.stream().limit(limit).toList();

        return latestBets;
    }
}
