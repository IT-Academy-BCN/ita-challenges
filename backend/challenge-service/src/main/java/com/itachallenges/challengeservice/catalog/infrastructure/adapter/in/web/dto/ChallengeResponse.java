package com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto;

public record ChallengeResponse(
        String id,
        String title,
        String description
) {}