package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InMemoryTicketRepositoryTest {

    private InMemoryTicketRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTicketRepository();
    }

    @Test
    void should_save_a_ticket() {
        Ticket ticket = Ticket.create("12345678", "Test Title", "Test Description");

        assertDoesNotThrow(() -> {
            repository.save(ticket);
        });
    }
}