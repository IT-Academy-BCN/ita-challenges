package com.ita.challenges.account.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void should_create_ticket_with_basic_information() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "Unable to access my account");

        assertNotNull(ticket.getId());
        assertEquals("user-1", ticket.getUserId());
        assertEquals("Login issue", ticket.getTitle());
        assertEquals("Unable to access my account", ticket.getDescription());
    }
    @Test
    void addComment_shouldThrowUnsupportedOperationException() {
        Ticket ticket = Ticket.create("user123", "Test Title", "Test Description");
        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> ticket.addComment("My comment")
        );
        assertEquals("addComment is not implemented yet.", exception.getMessage());
    }    
}
