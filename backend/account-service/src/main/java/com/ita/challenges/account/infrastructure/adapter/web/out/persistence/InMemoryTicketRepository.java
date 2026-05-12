package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTicketRepository implements TicketRepository {
    private final Map<String, Ticket> database = new ConcurrentHashMap();

    @Override
    public Ticket save(Ticket newTicket) {
        database.put(newTicket.getId(), newTicket);
        return newTicket;
    }

    @Override
    public List<Ticket> findAllByUserId(String userId) {
        return List.of();
    }

    @Override
    public void updateTicket(Ticket ticket) {

    }
}
