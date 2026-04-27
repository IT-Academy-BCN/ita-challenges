package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryChallengeRepositoryTest {

    @Test
    void update_shouldThrowUnsupportedOperationException() {
        InMemoryChallengeRepository repository = new InMemoryChallengeRepository();

        assertThrows(
                UnsupportedOperationException.class,
                () -> repository.update(null)
        );
    }

    @Test
    void delete_shouldThrowUnsupportedOperationException() {
        InMemoryChallengeRepository repository = new InMemoryChallengeRepository();
        assertThrows(UnsupportedOperationException.class, () -> repository.delete("any-id"));
    }
}
