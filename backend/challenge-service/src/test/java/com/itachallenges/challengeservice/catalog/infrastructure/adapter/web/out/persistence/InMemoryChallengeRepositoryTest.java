package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryChallengeRepositoryTest {

    private final InMemoryChallengeRepository repository = new InMemoryChallengeRepository();

    @Test
    void should_create_new_challenge() {
        Challenge newChallenge = Challenge.create("New Challenge Title", "New Challenge Description");

        Challenge result = repository.save(newChallenge);

        assertThat(result.getTitle().toString()).isEqualTo("New Challenge Title");
        assertThat(result.getDescription().toString()).isEqualTo("New Challenge Description");
    }
    
    @Test
    void delete_shouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> repository.delete("any-id"));
    }
}
