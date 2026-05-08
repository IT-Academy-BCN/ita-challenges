package com.itachallenges.challengeservice.submission.domain.valueobject;

import java.util.UUID;

public record SubmissionId(UUID value) {
    public SubmissionId {
        if (value == null) throw new IllegalArgumentException("SubmissionId cannot be null");
    }
    public static SubmissionId generate() { return new SubmissionId(UUID.randomUUID()); }
    public static SubmissionId of(String value) { return new SubmissionId(UUID.fromString(value)); }
    @Override
    public String toString() { return value.toString(); }
}
