package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/account/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findAll() {
        return ResponseEntity.ok(List.of());
    }
  
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> update(
            @PathVariable String id,
            @RequestBody TicketRequest ticketRequest) {
        return ticketRepository.findById(id)
                .map(existingTicket -> {
                    Ticket updatedTicket = Ticket.restore(
                            id,
                            existingTicket.getUserId(),
                            ticketRequest.title(),
                            ticketRequest.description()
                    );
                    Ticket savedTicket = ticketRepository.updateTicket(updatedTicket);
                    TicketResponse ticketResponse = new TicketResponse(
                            savedTicket.getId(),
                            savedTicket.getUserId(),
                            savedTicket.getTitle(),
                            savedTicket.getDescription()
                    );
                    return ResponseEntity.ok(ticketResponse);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
