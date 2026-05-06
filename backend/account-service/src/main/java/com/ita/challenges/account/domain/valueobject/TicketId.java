package com.ita.challenges.account.domain.valueobject;

import java.util.UUID;

public record TicketId(UUID value) {
    public TicketId {
        if (value == null) throw new IllegalArgumentException("TicketId cannot be null");
    }

    public static TicketId generate() {
        return new TicketId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
