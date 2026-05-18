package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import org.springframework.stereotype.Service;

@Service
public class SaveDraftSubmissionUseCaseHandler implements SaveDraftSubmissionUseCase {
    // TODO: inject SubmissionRepository

    @Override
    public void execute(SaveDraftSubmissionCommand command) {
        // TODO: create or update submission with IN_PROGRESS status
        // TODO: save submission
    }
}