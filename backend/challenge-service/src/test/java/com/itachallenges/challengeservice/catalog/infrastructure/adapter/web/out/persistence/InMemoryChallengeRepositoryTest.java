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
    void should_return_empty_list_when_no_challenges_exist() {
        List<Challenge> result = repository.findAll();

        assertThat(result).isEmpty();
    }
    void should_create_new_challenge() {
        Challenge newChallenge = Challenge.create("New Challenge Title", "New Challenge Description");

        Challenge result = repository.save(newChallenge);

        assertThat(result.getTitle().toString()).isEqualTo("New Challenge Title");
        assertThat(result.getDescription().toString()).isEqualTo("New Challenge Description");
    }

    @Test
    void findAll_should_throw_unsupported_operation_exception() {
        assertThrows(UnsupportedOperationException.class, repository::findAll);
    }

    @Test
    void should_update_existing_challenge() {
        Challenge original = Challenge.create("Old title", "Old description");

        repository.storage.put(original.getId(), original);

        Challenge updated = Challenge.restore(
                original.getId(),
                "New title",
                "New description"
        );

        Challenge result = repository.update(updated);

        assertThat(result.getTitle().toString()).isEqualTo("New title");
        assertThat(result.getDescription().toString()).isEqualTo("New description");
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