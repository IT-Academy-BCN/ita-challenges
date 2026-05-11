package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto;

import java.util.List;

public record FinalizeSubmissionRequest(String challengeId, String userId, List<String> content) {}
