package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Repository;

<<<<<<< 476-define-how-ticketing-provides-user-tickets
import java.util.List;
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
>>>>>>> develop

@Repository
public class InMemoryTicketRepository implements TicketRepository {

<<<<<<< 476-define-how-ticketing-provides-user-tickets
    @Override
    public List<Ticket> findAllByUserId(String userId) {
        throw new UnsupportedOperationException("findAllByUserId not implemented yet");
=======
    private final Map<String, Ticket> storage = new ConcurrentHashMap<>();

    @Override
    public List<Ticket> findAllByUserId(String userId) {
        return new ArrayList<>(storage.values().stream()
                .filter(ticket -> ticket.getUserId().equals(userId))
                .toList());
>>>>>>> develop
    }
  
    @Override
    public Ticket updateTicket(Ticket ticket) {
        throw new UnsupportedOperationException("updateTicket not implemented yet");
    }
}
