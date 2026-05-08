package com.itachallenges.challengeservice.activity.domain.event;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;

import java.time.Instant;

public record FavoriteRemoved(UserId userId, ChallengeId challengeId, Instant occurredAt) {
    public FavoriteRemoved(UserId userId, ChallengeId challengeId) {
        this(userId, challengeId, Instant.now());
    }
}
