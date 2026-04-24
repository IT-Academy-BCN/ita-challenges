package com.itachallenges.challengeservice.catalog.domain.valueobject;

public record ChallengeDescription(String value) {

    @Override
    public String toString() {
        return value;
    }
}