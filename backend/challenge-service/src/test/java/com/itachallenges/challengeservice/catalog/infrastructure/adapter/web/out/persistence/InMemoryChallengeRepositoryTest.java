package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryChallengeRepositoryTest {

    private final InMemoryChallengeRepository repository = new InMemoryChallengeRepository();

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
    }
   


    @Test
    void should_delete_existing_challenge() {
        Challenge challenge = Challenge.create("To be deleted", "Description");

        ChallengeId id = challenge.getId();
        repository.save(challenge);
        assertThat(repository.findAll()).hasSize(1);

        repository.delete(id);

        assertThat(repository.findAll()).isEmpty();
    }
}