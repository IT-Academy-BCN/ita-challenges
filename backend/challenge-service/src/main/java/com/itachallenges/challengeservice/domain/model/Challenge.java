package com.itachallenges.challengeservice.domain.model;

import com.itachallenges.challengeservice.domain.exception.InvalidChallengeDescriptionException;
import com.itachallenges.challengeservice.domain.exception.InvalidChallengeTitleException;
import com.itachallenges.challengeservice.domain.valueobject.ChallengeId;

import java.util.Objects;

public class Challenge {

    private final ChallengeId id;
    private final String title;
    private final String description;

    private Challenge(ChallengeId id, String title, String description) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.title = validateTitle(title);
        this.description = validateDescription(description);
    }

    public static Challenge createNew(String title, String description) {
        return new Challenge(ChallengeId.random(), title, description);
    }

    public static Challenge restore(ChallengeId id, String title, String description) {
        return new Challenge(id, title, description);
    }

    private static String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidChallengeTitleException("Challenge title cannot be empty");
        }
        return title.trim();
    }

    private static String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidChallengeDescriptionException("Challenge description cannot be empty");
        }
        return description.trim();
    }

    public ChallengeId id() { return id; }
    public String title() { return title; }
    public String description() { return description; }
}
