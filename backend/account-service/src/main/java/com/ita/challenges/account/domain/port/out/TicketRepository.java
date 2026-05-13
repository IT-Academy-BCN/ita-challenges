package com.ita.challenges.account.domain.port.out;

import com.ita.challenges.account.domain.model.Ticket;

import java.util.List;

public interface TicketRepository {
    List<Ticket> findAllByUserId(String userId);

    Ticket updateTicket(Ticket ticket);

    Ticket save(Ticket newTicket);
}
