package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryChallengeRepositoryTest {

    private final InMemoryChallengeRepository repository = new InMemoryChallengeRepository();

    @Test
    void should_return_empty_list_when_no_challenges_exist() {
        List<Challenge> result = repository.findAll();

        assertThat(result).isEmpty();
    }
}