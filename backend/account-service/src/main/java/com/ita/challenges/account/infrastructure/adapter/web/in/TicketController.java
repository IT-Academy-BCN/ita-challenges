package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketPatchRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketPatchResponse;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/account/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketController(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse createTicket(
            @RequestBody TicketRequest request,
            @AuthenticationPrincipal OAuth2User user) {

        String userId = user != null ? user.getAttribute("login") : "temp-user";

        return new TicketResponse(
                "temp-id",
                userId,
                request.title(),
                request.description()
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

    @PatchMapping("/{id}")
    public ResponseEntity<TicketPatchResponse> patchTicket(@PathVariable String id,
                                                           @RequestBody TicketPatchRequest patchRequest,
                                                           @AuthenticationPrincipal OAuth2User user){

        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String login = user.getAttribute("login");

        User appUser = userRepository.findByUsername(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User not registered"));

        if (appUser.userRole() != Role.MENTOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ticketRepository.findById(id)
                .map(existingTicket -> {

                    Ticket updatedTicket = existingTicket.withUpdates(
                            patchRequest.status(),
                            patchRequest.comment()
                    );

                    Ticket savedTicket = ticketRepository.updateTicket(updatedTicket);
                    return ResponseEntity.ok(new TicketPatchResponse(
                            savedTicket.getId(),
                            savedTicket.getUserId(),
                            savedTicket.getTitle(),
                            savedTicket.getDescription(),
                            savedTicket.getStatus(),
                            savedTicket.getComment()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
