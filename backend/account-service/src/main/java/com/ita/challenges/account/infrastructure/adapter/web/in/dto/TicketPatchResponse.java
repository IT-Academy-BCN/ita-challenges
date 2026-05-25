package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

import com.ita.challenges.account.domain.model.TicketStatus;

import java.time.Instant;

public record TicketPatchResponse(String id,
                                  String userId,
                                  String title,
                                  String comment,
                                  TicketStatus status,
                                  Instant createdAt,
                                  Instant updatedAt) {
}
