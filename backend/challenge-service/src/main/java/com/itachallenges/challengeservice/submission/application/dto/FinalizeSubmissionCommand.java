package com.itachallenges.challengeservice.submission.application.dto;

import java.util.List;

public record FinalizeSubmissionCommand(
        String challengeId,
        String userId,
        List<String> content
) {}
