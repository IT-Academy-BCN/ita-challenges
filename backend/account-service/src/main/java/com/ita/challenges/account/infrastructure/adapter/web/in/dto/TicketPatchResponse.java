package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

import com.ita.challenges.account.domain.model.TicketStatus;

public record TicketPatchResponse(String id, String userId, String title, String description, TicketStatus ticketStatus, String ticketComment) {
}
