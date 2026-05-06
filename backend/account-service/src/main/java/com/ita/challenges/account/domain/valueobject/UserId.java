package com.ita.challenges.account.domain.valueobject;

public record UserId(String value) {

    @Override
    public String toString() {
        return value;
    }
}
