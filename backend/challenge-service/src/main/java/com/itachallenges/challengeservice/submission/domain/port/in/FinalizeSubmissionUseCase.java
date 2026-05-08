package com.itachallenges.challengeservice.submission.domain.port.in;

import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;

public interface FinalizeSubmissionUseCase {
    // TODO: define the method signature to finalize a submission
    void execute(FinalizeSubmissionCommand command);
}
