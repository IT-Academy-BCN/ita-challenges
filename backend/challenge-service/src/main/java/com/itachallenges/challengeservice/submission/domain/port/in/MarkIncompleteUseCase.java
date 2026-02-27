package com.itachallenges.challengeservice.submission.domain.port.in;

import com.itachallenges.challengeservice.submission.application.dto.MarkIncompleteCommand;

public interface MarkIncompleteUseCase {
    // TODO: define the method signature to mark a submission as incomplete
    void execute(MarkIncompleteCommand command);
}
