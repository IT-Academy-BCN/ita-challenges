package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

import com.ita.challenges.account.domain.model.Ticket;

public record TicketResponse(String id, String userId, String title, String description) {

    public static TicketResponse fromEntity(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getUserId(),
                ticket.getTitle(),
                ticket.getDescription()
        );
    }
}
