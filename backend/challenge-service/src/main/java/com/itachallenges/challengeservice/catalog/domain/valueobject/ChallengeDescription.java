package com.itachallenges.challengeservice.catalog.domain.valueobject;

public record ChallengeDescription(String value) {

    public ChallengeDescription {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Description must not be null, empty or blank");
        }
    }
    @Override
    public String toString() {
        return value;
    }
}