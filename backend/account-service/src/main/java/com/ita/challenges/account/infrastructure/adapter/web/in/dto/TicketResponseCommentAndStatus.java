package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

import com.ita.challenges.account.domain.model.TicketStatus;

public record TicketResponseCommentAndStatus(

        String comment, TicketStatus status
) {
}
