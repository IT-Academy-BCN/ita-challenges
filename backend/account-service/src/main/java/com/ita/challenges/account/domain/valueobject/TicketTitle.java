package com.ita.challenges.account.domain.valueobject;

public record TicketTitle(String value) {

    @Override
    public String toString() {
        return value;
    }
}
