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

    private final SubmissionRepository repository;

    public SaveDraftSubmissionUseCaseHandler(SubmissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(SaveDraftSubmissionCommand command) {
        Submission submission = Submission.createInProgress(
                SubmissionId.generate(),
                ChallengeId.of(command.challengeId()),
                UserId.of(command.userId()),
                command.code()
        );
        repository.save(submission);
    }
}