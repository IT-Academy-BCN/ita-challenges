package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/account/tickets")
@CrossOrigin(origins = "http://localhost:4200")
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

        if (user == null || user.getAttribute("id") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated to create a ticket");
        }

        String userId = String.valueOf(user.getAttribute("id"));

        Ticket newTicket = Ticket.create(userId, request.title(), request.description());

        Ticket savedTicket = ticketRepository.save(newTicket);

        return new TicketResponse(
                savedTicket.getId(),
                savedTicket.getUserId(),
                savedTicket.getTitle(),
                savedTicket.getDescription()
        );
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findAll(@AuthenticationPrincipal OAuth2User user) {
        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.status(401).build();
        }

        String userId = user.getAttribute("login");

        List<TicketResponse> tickets = ticketRepository.findAllByUserId(userId)
                .stream()
                .map(ticket -> new TicketResponse(
                        ticket.getId(),
                        ticket.getUserId(),
                        ticket.getTitle(),
                        ticket.getDescription()
                ))
                .toList();

        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> update(
            @PathVariable String id,
            @RequestBody TicketRequest ticketRequest) {
        throw new UnsupportedOperationException("Update endpoint not implemented yet for ID: " + id);
    }

}