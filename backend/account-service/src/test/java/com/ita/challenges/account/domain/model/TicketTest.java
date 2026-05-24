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
    void addComment_should_store_and_overwrite_comment() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "Unable to access my account");

        Ticket commentedTicket = ticket.addComment("First comment");
        assertEquals("First comment", commentedTicket.getComment());

        Ticket overwrittenTicket = commentedTicket.addComment("Updated by mentor");
        assertEquals("Updated by mentor", overwrittenTicket.getComment());

        // old ticket keeps null comment (immutability demo)
        assertNull(ticket.getComment());
    }

    @Test
    void setStatus_should_update_the_status() {
        Ticket ticket = Ticket.create("user-1", "Login issue", "Unable to access my account");

        Ticket toInProgress = ticket.setStatus(TicketStatus.IN_PROGRESS);
        assertEquals(TicketStatus.IN_PROGRESS, toInProgress.getStatus());

        Ticket toResolved = toInProgress.setStatus(TicketStatus.RESOLVED);
        assertEquals(TicketStatus.RESOLVED, toResolved.getStatus());

        // old ticket has status OPEN still
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
    }
}
