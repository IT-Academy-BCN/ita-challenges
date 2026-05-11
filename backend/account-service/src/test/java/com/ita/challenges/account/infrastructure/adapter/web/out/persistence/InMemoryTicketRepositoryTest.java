package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import org.junit.jupiter.api.Test;

<<<<<<< 476-define-how-ticketing-provides-user-tickets
=======
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
>>>>>>> develop
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryTicketRepositoryTest {

    private final InMemoryTicketRepository repository = new InMemoryTicketRepository();

    @Test
<<<<<<< 476-define-how-ticketing-provides-user-tickets
    void findAllByUserId_should_throw_unsupported_operation_exception() {
        assertThrows(UnsupportedOperationException.class, () -> repository.findAllByUserId("user-1"));
    }
      
=======
    void should_return_empty_list_when_no_tickets_exist_for_user() {
        List<Ticket> result = repository.findAllByUserId("user-1");

        assertThat(result).isEmpty();
    }

>>>>>>> develop
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
