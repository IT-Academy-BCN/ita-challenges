package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class InMemoryUserRepositoryTest {
    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void should_return_empty_when_user_not_found() {
        assertThat(repository.findByUsername("unknown")).isEmpty();
    }

}