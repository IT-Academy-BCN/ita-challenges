package com.itachallenges.challengeservice.catalog.domain.port.in;

import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeResult;

public interface CreateChallengeUseCase {
    CreateChallengeResult create(CreateChallengeCommand command);
}
