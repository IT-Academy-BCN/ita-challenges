package com.itachallenges.challengeservice.submission.domain.port.out;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;

import java.util.Optional;

public interface SubmissionRepository {
    Submission save(Submission submission);
    Optional<Submission> findById(SubmissionId id);
    Optional<Submission> findByUserAndChallenge(UserId userId, ChallengeId challengeId);
    boolean existsFinalSubmission(UserId userId, ChallengeId challengeId);
}
