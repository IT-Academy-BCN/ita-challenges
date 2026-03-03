package com.itachallenges.challengeservice.catalog.domain.port.in;

import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeResponse;

public interface CreateChallengeUseCase {
    CreateChallengeResponse create(CreateChallengeCommand command);
}
