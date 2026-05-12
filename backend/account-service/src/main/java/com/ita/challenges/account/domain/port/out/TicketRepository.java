package com.ita.challenges.account.domain.port.out;

import com.ita.challenges.account.domain.model.Ticket;

import java.util.Collection;
import java.util.List;

public interface TicketRepository {
    Ticket save(Ticket newTicket);
    List<Ticket> findAllByUserId(String userId);
    void updateTicket(Ticket ticket);
}
