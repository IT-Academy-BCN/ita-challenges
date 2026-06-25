package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryChallengeRepositoryFilterTest {

    private InMemoryChallengeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryChallengeRepository();
    }

    @Test
    void should_return_only_challenges_with_given_language() {
        Challenge java1 = Challenge.create("Java 1", "Desc 1", ChallengeLanguage.JAVA, ChallengeDifficulty.EASY, "Sol 1");
        Challenge java2 = Challenge.create("Java 2", "Desc 2", ChallengeLanguage.JAVA, ChallengeDifficulty.MEDIUM, "Sol 2");
        Challenge python = Challenge.create("Python 1", "Desc 3", ChallengeLanguage.PYTHON, ChallengeDifficulty.HARD, "Sol 3");
        repository.save(java1);
        repository.save(java2);
        repository.save(python);

        List<Challenge> result = repository.findByLanguage(ChallengeLanguage.JAVA);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Challenge::getLanguage).containsOnly(ChallengeLanguage.JAVA);
    }

    @Test
    void should_return_empty_list_when_no_challenges_match_language() {
        repository.save(Challenge.create("Java only", "Desc", ChallengeLanguage.JAVA, ChallengeDifficulty.EASY, "Sol"));

        List<Challenge> result = repository.findByLanguage(ChallengeLanguage.PYTHON);

        assertThat(result).isEmpty();
    }
}