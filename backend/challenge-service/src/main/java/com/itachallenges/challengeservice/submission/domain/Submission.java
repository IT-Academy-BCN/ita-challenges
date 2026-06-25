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

    public static Submission restore(SubmissionId id, ChallengeId challengeId, UserId userId,
                                     SubmissionStatus status, String code,
                                     Instant createdAt, Instant updatedAt) {
        Submission submission = new Submission();
        submission.id = id;
        submission.challengeId = challengeId;
        submission.userId = userId;
        submission.status = status;
        submission.code = code;
        submission.createdAt = createdAt;
        submission.updatedAt = updatedAt;
        return submission;
    }

    public static Submission toSubmitted(Submission draft) {
        Submission submission = new Submission();
        submission.id = draft.id;
        submission.challengeId = draft.challengeId;
        submission.userId = draft.userId;
        submission.code = draft.code;
        submission.status = SubmissionStatus.SUBMITTED;
        submission.createdAt = draft.createdAt;
        submission.updatedAt = Instant.now();
        return submission;
    }

    public static Submission toInProgress(Submission existing) {
        Submission submission = new Submission();
        submission.id = existing.id;
        submission.challengeId = existing.challengeId;
        submission.userId = existing.userId;
        submission.code = existing.code;
        submission.status = SubmissionStatus.IN_PROGRESS;
        submission.createdAt = existing.createdAt;
        submission.updatedAt = Instant.now();
        return submission;
    }

}
