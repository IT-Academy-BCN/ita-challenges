package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;

public record ChallengeRequest(String title, String description, ChallengeDifficulty difficulty) {}
