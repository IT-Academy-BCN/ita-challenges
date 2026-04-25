package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryChallengeRepositoryTest {

    private final InMemoryChallengeRepository repository = new InMemoryChallengeRepository();

    @Test
    void should_update_existing_challenge() {

        // given
        Challenge original = Challenge.create("Old title", "Old description");

        // prepare existing state since update requires the challenge to exist
        repository.storage.put(original.getId(), original);

        Challenge updated = Challenge.restore(
                original.getId(),
                "New title",
                "New description"
        );

        // when
        Challenge result = repository.update(updated);

        // then
        assertThat(result.getTitle().toString()).isEqualTo("New title");
        assertThat(result.getDescription().toString()).isEqualTo("New description");
    void delete_shouldThrowUnsupportedOperationException() {
        InMemoryChallengeRepository repository = new InMemoryChallengeRepository();
        assertThrows(UnsupportedOperationException.class, () -> repository.delete("any-id"));
    }
}