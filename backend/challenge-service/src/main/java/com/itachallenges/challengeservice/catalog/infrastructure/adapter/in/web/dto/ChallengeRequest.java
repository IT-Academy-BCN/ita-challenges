package com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChallengeRequest(
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(max = 1000) String description
) {}