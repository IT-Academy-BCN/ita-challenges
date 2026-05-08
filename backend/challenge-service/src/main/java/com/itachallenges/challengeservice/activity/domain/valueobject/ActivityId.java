package com.itachallenges.challengeservice.activity.domain.valueobject;

import java.util.UUID;

public record ActivityId(UUID value) {
    public ActivityId {
        if (value == null) throw new IllegalArgumentException("UserActivityId cannot be null");
    }
    public static ActivityId generate() { return new ActivityId(UUID.randomUUID()); }
    public static ActivityId of(String value) { return new ActivityId(UUID.fromString(value)); }
    @Override
    public String toString() { return value.toString(); }
}
