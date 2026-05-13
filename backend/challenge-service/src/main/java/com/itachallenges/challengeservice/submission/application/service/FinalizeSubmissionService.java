package com.itachallenges.challengeservice.submission.application.service;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import org.springframework.stereotype.Service;

@Service
public class FinalizeSubmissionService implements FinalizeSubmissionUseCase {

    private final SubmissionRepository submissionRepository;

    public FinalizeSubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public void execute(FinalizeSubmissionCommand command) {
        UserId userId = UserId.of(command.userId());
        ChallengeId challengeId = ChallengeId.of(command.challengeId());

        Submission submission = submissionRepository
                .findByUserAndChallenge(userId, challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));

        submission.finalize(command.code());
        submissionRepository.save(submission);
    }
}