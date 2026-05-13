package com.itachallenges.challengeservice.submission.domain;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Submission {

    private SubmissionId id;
    private ChallengeId challengeId;
    private UserId userId;
    private SubmissionStatus status;
    private String code;
    private Instant createdAt;
    private Instant updatedAt;

    private final List<Object> domainEvents = new ArrayList<>();

    private Submission() {}

    public static Submission createInProgress(SubmissionId id, ChallengeId challengeId, UserId userId, String code) {
        // TODO: create and return a new Submission with IN_PROGRESS status
        return null;
    }

    public void finalize(String code) {
        if (this.status == SubmissionStatus.SUBMITTED) {
            throw new IllegalStateException("Submission already submitted");
        }
        this.code = code;
        this.status = SubmissionStatus.SUBMITTED;
        this.updatedAt = Instant.now();
    }

    public void markIncomplete() {
        // TODO: transition status to INCOMPLETE and emit SubmissionMarkedIncomplete event
        // TODO: guard against invalid transitions
    }

    public List<Object> pullDomainEvents() {
        return null;
    }

    public SubmissionId getId() { return id; }
    public ChallengeId getChallengeId() { return challengeId; }
    public UserId getUserId() { return userId; }
    public SubmissionStatus getStatus() { return status; }
    public String getCode() { return code; }
}