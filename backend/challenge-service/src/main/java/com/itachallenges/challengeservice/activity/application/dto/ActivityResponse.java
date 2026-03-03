package com.itachallenges.challengeservice.activity.application.dto;

public record ActivityResponse(
        String userId,
        String challengeId,
        boolean isFavorite,
        boolean isBookmark
) {}
