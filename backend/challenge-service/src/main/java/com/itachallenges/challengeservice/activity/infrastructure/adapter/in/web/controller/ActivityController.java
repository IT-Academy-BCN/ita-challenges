package com.itachallenges.challengeservice.activity.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.activity.domain.port.in.ToggleBookmarkUseCase;
import com.itachallenges.challengeservice.activity.domain.port.in.ToggleFavoriteUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ActivityController {

    private final ToggleFavoriteUseCase toggleFavoriteUseCase;
    private final ToggleBookmarkUseCase toggleBookmarkUseCase;

    public ActivityController(ToggleFavoriteUseCase toggleFavoriteUseCase,
                              ToggleBookmarkUseCase toggleBookmarkUseCase) {
        this.toggleFavoriteUseCase = toggleFavoriteUseCase;
        this.toggleBookmarkUseCase = toggleBookmarkUseCase;
    }

    // TODO: POST /{challengeId}/favorite  -> toggle favorite
    // TODO: POST /{challengeId}/bookmark  -> toggle bookmark
}
