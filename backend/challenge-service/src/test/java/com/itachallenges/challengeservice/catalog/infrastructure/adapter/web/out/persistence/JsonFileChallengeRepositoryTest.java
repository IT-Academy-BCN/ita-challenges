package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JsonFileChallengeRepositoryTest {

    private static final String TEST_FILE = "challenges-test.json";

    private JsonFileChallengeRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        repository = new JsonFileChallengeRepository(
                TEST_FILE,
                new ObjectMapper()
        );
    }

    @AfterEach
    void cleanUp() {
        new File(TEST_FILE).delete();
    }

    @Test
    void save_should_store_challenge_in_json_file() throws IOException {

        Challenge challenge = Challenge.create(
                "Clean Code",
                "Write readable code",
                ChallengeDifficulty.EASY
        );

        repository.save(challenge);

        assertThat(new File(TEST_FILE)).exists();
        assertThat(repository.findAll()).hasSize(1);
        assertThat(repository.findAll().get(0).getTitle().toString())
                .isEqualTo("Clean Code");
    }
}
