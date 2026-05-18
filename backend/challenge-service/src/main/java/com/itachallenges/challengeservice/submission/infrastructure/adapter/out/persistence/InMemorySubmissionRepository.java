package com.itachallenges.challengeservice.submission.infrastructure.adapter.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySubmissionRepository implements SubmissionRepository {

    private final Map<SubmissionId, Submission> storage = new ConcurrentHashMap<>();

    @Override
    public Submission save(Submission submission) {
        storage.put(submission.getId(), submission);
        return submission;
    }

    @Override
    public Optional<Submission> findById(SubmissionId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Submission> findByUserAndChallenge(UserId userId, ChallengeId challengeId) {
        // TODO: find submission by userId and challengeId
        return Optional.empty();
    }

    @Override
    public boolean existsFinalSubmission(UserId userId, ChallengeId challengeId) {
        // TODO: check if a FINAL submission exists for this userId and challengeId
        return false;
    }
}
