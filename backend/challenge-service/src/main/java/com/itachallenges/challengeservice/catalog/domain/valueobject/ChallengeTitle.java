package com.itachallenges.challengeservice.catalog.domain.valueobject;

public record ChallengeTitle(String value) {

    @Override
    public String toString() {
        return value;
    }
}