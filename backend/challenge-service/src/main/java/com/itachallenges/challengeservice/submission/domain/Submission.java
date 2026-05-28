package com.itachallenges.challengeservice.submission.domain;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionStatus;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Submission {

    private SubmissionId id;
    private ChallengeId challengeId;
    private UserId userId;
    private SubmissionStatus status;
    private String code;
    private Instant createdAt;
    private Instant updatedAt;

    private Submission() {}

    public static Submission createInProgress(SubmissionId id, ChallengeId challengeId, UserId userId, String code) {
        Submission submission = new Submission();
        submission.id = id;
        submission.challengeId = challengeId;
        submission.userId = userId;
        submission.code = code;
        submission.status = SubmissionStatus.IN_PROGRESS;
        submission.createdAt = Instant.now();
        submission.updatedAt = Instant.now();
        return submission;
    }

    public static Submission createSubmitted(SubmissionId id, ChallengeId challengeId, UserId userId, String code) {
        Submission submission = new Submission();
        submission.id = id;
        submission.challengeId = challengeId;
        submission.userId = userId;
        submission.code = code;
        submission.status = SubmissionStatus.SUBMITTED;
        submission.createdAt = Instant.now();
        submission.updatedAt = Instant.now();
        return submission;
    }

}
