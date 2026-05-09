package com.itachallenges.challengeservice.submission.application.dto;

import java.util.List;

public record SaveDraftSubmissionCommand(
        String challengeId,
        String userId,
        List<String> content
) {}
