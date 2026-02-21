package com.ita.challenges.challenge.domain.port.in;

import com.ita.challenges.challenge.application.dto.CreateChallengeCommand;
import com.ita.challenges.challenge.application.dto.CreateChallengeResult;

public interface CreateChallengeUseCase {
    CreateChallengeResult create(CreateChallengeCommand command);
}
