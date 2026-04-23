package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChallengeRequest(
        @NotBlank
        @Size (min = 3, max = 100)
        String title,

        @NotBlank
        @Size (min = 3,max = 1000)
        String description) {}
