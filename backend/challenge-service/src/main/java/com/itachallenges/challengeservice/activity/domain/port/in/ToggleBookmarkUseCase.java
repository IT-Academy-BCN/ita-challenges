package com.itachallenges.challengeservice.activity.domain.port.in;

import com.itachallenges.challengeservice.activity.application.dto.ToggleActivityCommand;

public interface ToggleBookmarkUseCase {
    void execute(ToggleActivityCommand command);
}