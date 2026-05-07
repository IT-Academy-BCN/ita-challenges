package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryTicketRepository implements TicketRepository {

    @Override
    public List<Ticket> findAllByUserId(String userId) {
        throw new UnsupportedOperationException("findAllByUserId not implemented yet");
    }
}
