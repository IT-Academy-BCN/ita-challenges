package com.itachallenges.challengeservice.stat.domain.valueobject;

import java.util.UUID;

public record ChallengeStatsId(UUID value) {
    public ChallengeStatsId {
        if (value == null) throw new IllegalArgumentException("ChallengeStatsId cannot be null");
    }
    public static ChallengeStatsId generate() { return new ChallengeStatsId(UUID.randomUUID()); }
    public static ChallengeStatsId of(String value) { return new ChallengeStatsId(UUID.fromString(value)); }

    @Override
    public String toString() { return value.toString(); }
}