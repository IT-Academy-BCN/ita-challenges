package com.ita.challenges.account.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void should_create_ticket_with_basic_information() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "Unable to access my account");

        assertNotNull(ticket.getId());
        assertEquals("user-1", ticket.getUserId().toString());
        assertEquals("Login issue", ticket.getTitle().toString());
        assertEquals("Unable to access my account", ticket.getDescription().toString());
    }
}
