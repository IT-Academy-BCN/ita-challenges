package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.*;
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
    public ResponseEntity<?> createTicket(
            @RequestBody TicketRequest request,
            @AuthenticationPrincipal OAuth2User user) {

        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.<TicketResponse>status(HttpStatus.UNAUTHORIZED).build();
        }
        if (request.title().isBlank() || request.description().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No empty fields allowed");
        }

        String userId = user.getAttribute("login");

        Ticket newTicket = Ticket.create(userId, request.title(), request.description());
        Ticket savedTicket = ticketRepository.save(newTicket);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TicketResponse(
                        savedTicket.getId(),
                        savedTicket.getUserId(),
                        savedTicket.getMentorAssignedId(),
                        savedTicket.getTitle(),
                        savedTicket.getDescription()
                ));
    }

    @GetMapping
    public ResponseEntity<List<TicketPatchResponse>> findAll(@AuthenticationPrincipal OAuth2User user) {
        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.status(401).build();
        }

        String userId = user.getAttribute("login");

        List<TicketPatchResponse> tickets = ticketRepository.findAllByUserId(userId)
                .stream()
                .map(ticket -> new TicketPatchResponse(
                        ticket.getId(),
                        ticket.getUserId(),
                        ticket.getMentorAssignedId(),
                        ticket.getTitle(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getComment()
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
                            existingTicket.getMentorAssignedId(),
                            ticketRequest.title(),
                            ticketRequest.description()
                    );
                    Ticket savedTicket = ticketRepository.updateTicket(updatedTicket);
                    return ResponseEntity.ok(new TicketResponse(
                            savedTicket.getId(),
                            savedTicket.getUserId(),
                            savedTicket.getMentorAssignedId(),
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
                            ticket.getMentorAssignedId(),
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
                            savedTicket.getMentorAssignedId(),
                            savedTicket.getTitle(),
                            savedTicket.getDescription(),
                            savedTicket.getStatus(),
                            savedTicket.getComment()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<TicketPatchResponse> assignMentor(@PathVariable String id,
                                          @RequestBody TicketAssignRequest assignRequest,
                                          @AuthenticationPrincipal OAuth2User user) {

        if (user == null || user.getAttribute("login") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String login = user.getAttribute("login");

        userRepository.findByUsername(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User not registered"));

        if (assignRequest.mentorAssignedId() != null && !assignRequest.mentorAssignedId().isBlank()) {
            userRepository.findByUsername(assignRequest.mentorAssignedId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The assigned mentor does not exist"));
        }

        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        Ticket updatedTicket = existingTicket.assignMentor(assignRequest.mentorAssignedId());
        Ticket savedTicket = ticketRepository.updateTicket(updatedTicket);

        return ResponseEntity.ok(new TicketPatchResponse(
                savedTicket.getId(),
                savedTicket.getUserId(),
                savedTicket.getMentorAssignedId(),
                savedTicket.getTitle(),
                savedTicket.getDescription(),
                savedTicket.getStatus(),
                savedTicket.getComment()
        ));
    }
}
