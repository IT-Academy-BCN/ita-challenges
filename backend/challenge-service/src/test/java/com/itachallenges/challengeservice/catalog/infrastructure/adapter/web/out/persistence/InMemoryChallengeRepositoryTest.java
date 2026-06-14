package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class InMemoryChallengeRepositoryTest {

    private InMemoryChallengeRepository repository;
    private Challenge challenge;

    @BeforeEach
    void setUp() {
        repository = new InMemoryChallengeRepository();
        challenge = Challenge.create("Clean Code Challenge", "A challenge about writing clean and maintainable code", ChallengeLanguage.JAVA, ChallengeDifficulty.EASY, "Challenge solution");
    }

    @Test
    void should_return_empty_list_when_no_challenges_exist() {
        List<Challenge> result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void should_create_new_challenge() {
        Challenge newChallenge = Challenge.create("New Challenge Title", "New Challenge Description", ChallengeLanguage.JAVA, ChallengeDifficulty.EASY, "New Challenge Solution");
        Challenge result = repository.save(newChallenge);

        assertThat(result.getTitle().toString()).isEqualTo("New Challenge Title");
        assertThat(result.getDescription().toString()).isEqualTo("New Challenge Description");
        assertThat(result.getSolution().toString()).isEqualTo("New Challenge Solution");
    }

    @Test
    void should_update_existing_challenge() {
        Challenge original = repository.save(Challenge.create("Old title", "Old description", ChallengeLanguage.JAVA, ChallengeDifficulty.EASY, "Old solution"));

        Challenge updated = Challenge.restore(
                original.getId(),
                "New title",
                "New description",
                ChallengeLanguage.JAVA,
                ChallengeDifficulty.EASY,
                "New solution"
        );

        Challenge result = repository.update(updated);

        assertThat(result.getTitle().toString()).isEqualTo("New title");
        assertThat(result.getDescription().toString()).isEqualTo("New description");
        assertThat(result.getSolution().toString()).isEqualTo("New solution");
    }


    @Test
    void should_delete_existing_challenge() {
        Challenge challenge = Challenge.create("To be deleted", "Description", ChallengeLanguage.JAVA, ChallengeDifficulty.EASY, "Solution");

        ChallengeId id = challenge.getId();
        repository.save(challenge);
        assertThat(repository.findAll()).hasSize(1);

        repository.delete(id);

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    void save_should_store_challenge() {
        repository.save(challenge);

        List<Challenge> result = repository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(challenge.getId());
    }

    @Test
    void should_throw_when_updating_non_existing_challenge() {
        ChallengeId nonExistentId = ChallengeId.generate();
        Challenge unSavedChallenge = Challenge.restore(
                nonExistentId,
                "Title",
                "Description",
                ChallengeLanguage.JAVA,
                ChallengeDifficulty.EASY,
                "Solution"
        );

        assertThatThrownBy(() -> repository.update(unSavedChallenge))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Challenge not found with id: " + nonExistentId);
    }
}


