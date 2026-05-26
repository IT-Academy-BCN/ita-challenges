package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/account/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody TicketRequest request,
            @AuthenticationPrincipal OAuth2User user) {

        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.<TicketResponse>status(HttpStatus.UNAUTHORIZED).build();
        }

        String userId = user.getAttribute("login");

        Ticket newTicket = Ticket.create(userId, request.title(), request.description());
        Ticket savedTicket = ticketRepository.save(newTicket);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TicketResponse(
                        savedTicket.getId(),
                        savedTicket.getUserId(),
                        savedTicket.getTitle(),
                        savedTicket.getDescription()
                ));
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
            @RequestBody TicketRequest ticketRequest,
            @AuthenticationPrincipal OAuth2User user) {
        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.status(401).build();
        }

        String currentUserId = user.getAttribute("login");

        return ticketRepository.findById(id)
                .map(existingTicket -> {
                    if (!existingTicket.getUserId().equals(currentUserId)) {
                        return ResponseEntity.status(403).<TicketResponse>build();
                    }
                    Ticket updatedTicket = Ticket.restore(
                            id,
                            existingTicket.getUserId(),
                            ticketRequest.title(),
                            ticketRequest.description()
                    );
                    Ticket savedTicket = ticketRepository.updateTicket(updatedTicket);
                    return ResponseEntity.ok(new TicketResponse(
                            savedTicket.getId(),
                            savedTicket.getUserId(),
                            savedTicket.getTitle(),
                            savedTicket.getDescription()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity <TicketResponse> findById( @PathVariable String id,
                                                     @AuthenticationPrincipal OAuth2User user) {

        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.status(401).build();
        }

        return ticketRepository.findById(id)
                .map(ticket -> {
                    return ResponseEntity.ok(new TicketResponse(
                            ticket.getId(),
                            ticket.getUserId(),
                            ticket.getTitle(),
                            ticket.getDescription()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
