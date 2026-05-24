package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

import com.ita.challenges.account.domain.model.TicketStatus;

public record TicketPatchRequest(String title,
                                 String description,
                                 TicketStatus ticketStatus,
                                 String ticketResponse) {
}
