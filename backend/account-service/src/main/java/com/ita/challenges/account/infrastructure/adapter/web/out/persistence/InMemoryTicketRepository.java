package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTicketRepository implements TicketRepository {

    // Usamos ConcurrentHashMap que es más seguro para aplicaciones web
    private final Map<String, Ticket> storage = new ConcurrentHashMap<>();

    @Override
    public List<Ticket> findAllByUserId(String userId) {
        return new ArrayList<>(storage.values().stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .toList());
    }

    @Override
    public Ticket save(Ticket newTicket) {
        storage.put(newTicket.getId(), newTicket);
        return newTicket;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        storage.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return Optional.ofNullable(storage.get(id));
}