package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTicketRepositoryTest {
    private final InMemoryTicketRepository repository = new InMemoryTicketRepository();

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
