package com.ita.challenges.account.domain.valueobject;

public record TicketDescription(String value) {

    @Override
    public String toString() {
        return value;
    }
}
