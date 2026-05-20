package com.ita.challenges.account.application.service;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.in.CreateTicketUseCase;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTicketService implements CreateTicketUseCase {

    private final TicketRepository ticketRepository;

    public CreateTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket createTicket(String userId, String title, String description) {
        Ticket newTicket = Ticket.create(userId, title, description);
        return ticketRepository.save(newTicket);
    }
}