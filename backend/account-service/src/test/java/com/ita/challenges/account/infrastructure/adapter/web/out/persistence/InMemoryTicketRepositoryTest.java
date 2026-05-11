package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryTicketRepositoryTest {

    private final InMemoryTicketRepository repository = new InMemoryTicketRepository();

    @Test
    void findAllByUserId_should_throw_unsupported_operation_exception() {
        assertThrows(UnsupportedOperationException.class, () -> repository.findAllByUserId("user-1"));
    }
      
    @Test
    void updateTicket_should_throw_unsupported_operation_exception() {

        Ticket ticket = Ticket.create("user-1", "Update Task", "Description");

        UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class, () -> {
                    repository.updateTicket(ticket);
                });

        assertEquals("updateTicket not implemented yet", exception.getMessage());
    }
}
