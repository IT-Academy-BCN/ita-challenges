package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse createTicket(
            @RequestBody TicketRequest request,
            @AuthenticationPrincipal OAuth2User user) {

        String userId = "anonymous";
        if (user != null && user.getAttribute("id") != null) {
            userId = user.getAttribute("id").toString();
        }

        Ticket newTicket = Ticket.create(userId, request.title(), request.description());

        Ticket savedTicket = ticketRepository.save(newTicket);

        return new TicketResponse(
                savedTicket.getId(),
                savedTicket.getUserId(),
                savedTicket.getTitle(),
                savedTicket.getDescription()
        );
    }
}