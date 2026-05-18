package com.itachallenges.challengeservice.submission.domain;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionStatus;
import lombok.Getter;

import java.time.Instant;


public class Submission {

    @Getter
    private SubmissionId id;
    @Getter
    private ChallengeId challengeId;
    @Getter
    private UserId userId;
    @Getter
    private SubmissionStatus status;
    @Getter
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

}
