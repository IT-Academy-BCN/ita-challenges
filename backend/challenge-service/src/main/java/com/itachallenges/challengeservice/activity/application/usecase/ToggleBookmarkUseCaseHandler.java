package com.itachallenges.challengeservice.activity.application.usecase;

import com.itachallenges.challengeservice.activity.application.dto.ToggleActivityCommand;
import com.itachallenges.challengeservice.activity.domain.port.in.ToggleBookmarkUseCase;
import org.springframework.stereotype.Service;

@Service
public class ToggleBookmarkUseCaseHandler implements ToggleBookmarkUseCase {
    // TODO: inject UserActivityRepository and any other dependencies

    @Override
    public void execute(ToggleActivityCommand command) {
        // TODO: load or create UserActivity for this userId and challengeId
        // TODO: call userActivity.toggleBookmark()
        // TODO: save userActivity
        // TODO: publish domain events
    }
}
