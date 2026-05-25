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
    void withUpdates_should_update_only_comment() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "desc");
        Ticket updated = ticket.withUpdates(null, "Mentor comment");
        assertEquals("Mentor comment", updated.getComment());
        assertEquals(ticket.getStatus(), updated.getStatus()); // unchanged
    }
    @Test
    void withUpdates_should_update_only_status() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "desc");
        Ticket updated = ticket.withUpdates(TicketStatus.RESOLVED, null);
        assertEquals(TicketStatus.RESOLVED, updated.getStatus());
        assertEquals(ticket.getComment(), updated.getComment()); // unchanged (null initially)
    }
    @Test
    void withUpdates_should_update_both_status_and_comment() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "desc");
        Ticket updated = ticket.withUpdates(TicketStatus.IN_PROGRESS, "Checking this");
        assertEquals(TicketStatus.IN_PROGRESS, updated.getStatus());
        assertEquals("Checking this", updated.getComment());
    }
    @Test
    void withUpdates_should_not_change_anything_if_both_params_null() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "desc");
        Ticket updated = ticket.withUpdates(null, null);
        assertEquals(ticket.getStatus(), updated.getStatus());
        assertEquals(ticket.getComment(), updated.getComment());
    }
}

