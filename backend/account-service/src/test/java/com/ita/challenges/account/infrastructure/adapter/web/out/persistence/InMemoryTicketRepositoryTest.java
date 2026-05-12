package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTicketRepositoryTest {

    private final InMemoryTicketRepository repository = new InMemoryTicketRepository();

    @Test
    void should_return_empty_list_when_no_tickets_exist_for_user() {

        List<Ticket> result = repository.findAllByUserId("user-1");

        assertThat(result).isEmpty();
    }

    @Test
    void updateTicket_should_throw_unsupported_operation_exception() {

        Ticket ticket = Ticket.create("user-1", "Update Task", "Description");

        UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class,
                        () -> repository.updateTicket(ticket));

        assertEquals("updateTicket not implemented yet", exception.getMessage());
    }

    @Test
    void should_find_ticket_by_id_successfully() {
        Ticket ticket = Ticket.create("user-456", "Test Title", "Test Desc");
        repository.save(ticket);

        Optional<Ticket> found = repository.findById(ticket.getId());

        assertTrue(found.isPresent(), "Ticket existing");
        assertEquals(ticket.getId(), found.get().getId());
        assertEquals("user-456", found.get().getUserId());
    }

    @Test
    void should_return_empty_when_ticket_id_does_not_exist() {
        String nonExistentId = "invalid-id";
        Optional<Ticket> found = repository.findById(nonExistentId);
        assertTrue(found.isEmpty(), "Ticket should not be found");
    }

    @Test
    void should_restore_ticket_correctly() {
        String id = "123";
        String userId = "user-1";
        String title = "Restored Title";
        String desc = "Restored Desc";

        Ticket restored = Ticket.restore(id, userId, title, desc);

        assertNotNull(restored);
        assertEquals(id, restored.getId());
        assertEquals(userId, restored.getUserId());
        assertEquals(title, restored.getTitle());
        assertEquals(desc, restored.getDescription());
    }
}
