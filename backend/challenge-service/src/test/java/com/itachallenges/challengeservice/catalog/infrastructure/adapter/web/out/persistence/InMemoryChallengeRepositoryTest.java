package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryChallengeRepositoryTest {

    private InMemoryChallengeRepository repository;
    private Challenge challenge;

    @BeforeEach
    void setUp() {
        repository = new InMemoryChallengeRepository();
        challenge = Challenge.create("Clean Code Challenge", "A challenge about writing clean and maintainable code");
    }

    @Test
    void save_should_store_challenge() {
        repository.save(challenge);

        List<Challenge> result = repository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(challenge.getId());
    }

    @Test
    void findAll_should_return_empty_when_no_challenges() {
        assertThat(repository.findAll()).isEmpty();
    }
    
    @Test
    void delete_shouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> repository.delete("any-id"));
    }
}
