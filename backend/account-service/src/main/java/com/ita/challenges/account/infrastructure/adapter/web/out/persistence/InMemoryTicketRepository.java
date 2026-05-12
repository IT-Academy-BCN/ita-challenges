package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryTicketRepository implements TicketRepository {
    private final Map<String, Ticket> database = new HashMap<>();

    @Override
    public Ticket save(Ticket newTicket) {
        database.put(newTicket.getId(), newTicket);
        return newTicket;
    }
}
