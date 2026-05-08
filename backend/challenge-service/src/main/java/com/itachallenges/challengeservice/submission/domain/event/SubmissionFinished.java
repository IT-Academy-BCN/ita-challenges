package com.itachallenges.challengeservice.submission.domain.event;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;

import java.time.Instant;

public record SubmissionFinished(
        SubmissionId submissionId,
        ChallengeId challengeId,
        UserId userId,
        Instant occurredAt
) {
    public SubmissionFinished(SubmissionId submissionId, ChallengeId challengeId, UserId userId) {
        this(submissionId, challengeId, userId, Instant.now());
    }
}