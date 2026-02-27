package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.submission.application.dto.MarkIncompleteCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.MarkIncompleteUseCase;

public class MarkIncompleteUseCaseHandler implements MarkIncompleteUseCase {
    // TODO: inject SubmissionRepository and any other dependencies

    @Override
    public void execute(MarkIncompleteCommand command) {
        // TODO: load submission
        // TODO: call submission.markIncomplete()
        // TODO: save submission
        // TODO: publish domain events
    }
}
