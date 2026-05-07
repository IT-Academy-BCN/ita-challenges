package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryTicketRepositoryTest {

    private final InMemoryTicketRepository repository = new InMemoryTicketRepository();

    @Test
    void findAllByUserId_should_throw_unsupported_operation_exception() {
        assertThrows(UnsupportedOperationException.class, () -> repository.findAllByUserId("user-1"));
    }
}
