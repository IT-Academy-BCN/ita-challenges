package com.ita.challenges.account.domain.port.out;

import com.ita.challenges.account.domain.model.Ticket;

public interface TicketRepository {
    Ticket save(Ticket newTicket);
}
