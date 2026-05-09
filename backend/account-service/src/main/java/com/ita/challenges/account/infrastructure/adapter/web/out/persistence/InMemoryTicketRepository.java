package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTicketRepository implements TicketRepository {
    @Override
    public Ticket save(Ticket newTicket) {
        throw new UnsupportedOperationException("Save endpoint not implemented yet");
    }
}
