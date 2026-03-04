package com.itachallenges.challengeservice.stat.application.dto;

public record ChallengeStatsResponse(
        String challengeId,
        long timesDone,
        long favorites,
        long bookmarks
) {}
