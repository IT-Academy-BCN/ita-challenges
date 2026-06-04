package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

public record UserProfileResponse(
        String username,
        String avatarUrl
) {}