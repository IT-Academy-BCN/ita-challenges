package com.itachallenges.challengeservice.catalog.domain.valueobject;

import com.itachallenges.challengeservice.catalog.domain.exception.InvalidChallengeDescriptionException;

public record ChallengeDescription(String value) {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 1000;

    public ChallengeDescription {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidChallengeDescriptionException("Challenge description cannot be empty");
        }

        value = value.trim();
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidChallengeDescriptionException(
                    "Challenge title must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long"
            );
        }
    }

    @Override
    public String toString() {
        return value;
    }
}