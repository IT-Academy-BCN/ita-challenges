package com.itachallenges.challengeservice.catalog.domain.valueobject;

public record ChallengeTitle(String value) {

    public ChallengeTitle {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Title must not be null, empty or blank.");
        }
    }
    @Override
    public String toString() {
        return value;
    }
}