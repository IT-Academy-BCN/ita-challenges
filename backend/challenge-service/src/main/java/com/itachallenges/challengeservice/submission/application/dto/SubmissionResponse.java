package com.itachallenges.challengeservice.submission.application.dto;

public record SubmissionResponse(
        String id,
        String challengeId,
        String userId,
        String status,
        String code
) {}
