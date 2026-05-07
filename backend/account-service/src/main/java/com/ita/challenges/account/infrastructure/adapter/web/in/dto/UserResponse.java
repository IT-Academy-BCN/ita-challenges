package com.ita.challenges.account.infrastructure.adapter.web.in.dto;

import com.ita.challenges.account.domain.model.Role;

public record UserResponse(
        String username,
        Role role
) {}
