package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTicketRepository implements TicketRepository {

    private final Map<String, Ticket> storage = new ConcurrentHashMap<>();

    @Override
    public List<Ticket> findAllByUserId(String userId) {
        return new ArrayList<>(storage.values().stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .toList());
    }
  
    @Override
    public Ticket updateTicket(Ticket ticket) {
        throw new UnsupportedOperationException("updateTicket not implemented yet");
    }
}
