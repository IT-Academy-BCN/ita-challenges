package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findAll() {
        return ResponseEntity.ok(List.of());
    }
  
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> update(
            @PathVariable String id,
            @RequestBody TicketRequest ticketRequest) {
        throw new UnsupportedOperationException("Update endpoint not implemented yet for ID: " + id);
    }

}
