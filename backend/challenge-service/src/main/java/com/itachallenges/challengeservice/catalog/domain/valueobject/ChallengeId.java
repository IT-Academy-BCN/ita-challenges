package com.itachallenges.challengeservice.catalog.domain.valueobject;

import java.util.UUID;

public record ChallengeId(UUID value) {

    public ChallengeId {
        if (value == null) {
            throw new IllegalArgumentException("ChallengeId cannot be null");
        }
    }

    public static ChallengeId random() {
        return new ChallengeId(UUID.randomUUID());
    }

    public static ChallengeId from(String raw) {
        return new ChallengeId(UUID.fromString(raw));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}