package com.itachallenges.challengeservice.catalog.domain.valueobject;

public record ChallengeSolution(String value) {

    public ChallengeSolution {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("The challenge solution must not be null, empty or blank.");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}