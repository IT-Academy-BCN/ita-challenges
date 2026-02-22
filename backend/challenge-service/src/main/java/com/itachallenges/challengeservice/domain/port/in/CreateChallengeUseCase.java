package com.itachallenges.challengeservice.domain.port.in;

import com.itachallenges.challengeservice.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.application.dto.CreateChallengeResult;

public interface CreateChallengeUseCase {
    CreateChallengeResult create(CreateChallengeCommand command);
}
