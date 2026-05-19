package com.ita.challenges.account.domain.port.in;

import com.ita.challenges.account.domain.model.Ticket;

public interface CreateTicketUseCase {
    Ticket createTicket(String userId, String title, String description);
}