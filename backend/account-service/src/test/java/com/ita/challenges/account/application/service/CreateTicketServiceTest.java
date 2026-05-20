package com.ita.challenges.account.application.service;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.model.TicketStatus;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CreateTicketService createTicketService;

    @Test
    void should_create_and_save_ticket_successfully() {
        String userId = "user-123";
        String title = "Fix login issue";
        String description = "The login button is not responding on mobile devices.";
        Instant now = Instant.now();

        Ticket mockSavedTicket = Ticket.restore(
                "ticket-999",
                userId,
                title,
                description,
                TicketStatus.OPEN,
                null,
                now,
                now
        );

        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockSavedTicket);

        Ticket result = createTicketService.createTicket(userId, title, description);

        assertNotNull(result);
        assertEquals("ticket-999", result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(TicketStatus.OPEN, result.getStatus());

        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
}