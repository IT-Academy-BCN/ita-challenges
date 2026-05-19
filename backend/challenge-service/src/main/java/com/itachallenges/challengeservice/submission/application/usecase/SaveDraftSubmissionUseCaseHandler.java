package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import org.springframework.stereotype.Service;

@Service
public class SaveDraftSubmissionUseCaseHandler implements SaveDraftSubmissionUseCase {

    private final SubmissionRepository submissionRepository;

    public SaveDraftSubmissionUseCaseHandler(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public void execute(SaveDraftSubmissionCommand command) {
        UserId userId = UserId.of(command.userId());
        ChallengeId challengeId = ChallengeId.of(command.challengeId());

        Submission submission = Submission.createSubmitted(SubmissionId.generate(), challengeId, userId, command.code());

        submissionRepository.save(submission);
    }
}