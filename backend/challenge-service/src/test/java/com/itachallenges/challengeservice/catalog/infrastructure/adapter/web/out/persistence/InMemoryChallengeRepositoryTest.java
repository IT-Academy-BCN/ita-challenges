package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryChallengeRepositoryTest {

    private final InMemoryChallengeRepository repository = new InMemoryChallengeRepository();

    @Test
    void save_should_throw_unsupported_operation_exception() {
        Challenge newChallenge = Challenge.create("New Challenge Title", "New Challenge Description");
        assertThrows(UnsupportedOperationException.class, () -> repository.save(newChallenge));
    }
}
