package com.itachallenges.challengeservice.submission.domain.port.in;

import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;

public interface SaveDraftSubmissionUseCase {
    // TODO: define the method signature to save a draft submission
    void execute(SaveDraftSubmissionCommand command);
}
