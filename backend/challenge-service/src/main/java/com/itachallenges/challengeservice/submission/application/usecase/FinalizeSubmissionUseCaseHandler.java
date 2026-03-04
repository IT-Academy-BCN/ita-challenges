package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import org.springframework.stereotype.Service;

@Service
public class FinalizeSubmissionUseCaseHandler implements FinalizeSubmissionUseCase {
    // TODO: inject SubmissionRepository and any other dependencies

    @Override
    public void execute(FinalizeSubmissionCommand command) {
        // TODO: check no FINAL submission exists for this user/challenge
        // TODO: load or create submission
        // TODO: call submission.finalize()
        // TODO: save submission
        // TODO: publish domain events
    }
}
