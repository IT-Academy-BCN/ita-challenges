package com.itachallenges.challengeservice.shared.domain.valueobject;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        if (value == null) throw new IllegalArgumentException("UserId cannot be null");
    }
    public static UserId generate() { return new UserId(UUID.randomUUID()); }
    public static UserId of(String value) { return new UserId(UUID.fromString(value)); }
    @Override
    public String toString() { return value.toString(); }
}