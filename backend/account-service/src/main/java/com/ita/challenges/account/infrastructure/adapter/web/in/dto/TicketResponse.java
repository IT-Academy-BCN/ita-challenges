package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

import com.ita.challenges.account.domain.model.TicketStatus;

public record TicketResponse(String id, String userId, String title, String description, String comment, TicketStatus status) {}
