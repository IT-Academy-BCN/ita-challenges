package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryChallengeRepositoryTest {

    @Test
    void delete_shouldThrowUnsupportedOperationException() {
        InMemoryChallengeRepository repository = new InMemoryChallengeRepository();
        assertThrows(UnsupportedOperationException.class, () -> repository.delete("any-id"));
    }
}
