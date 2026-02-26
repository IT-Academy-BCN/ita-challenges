package com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.catalog.domain.port.in.CreateChallengeUseCase;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto.CreateChallengeRequest;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto.CreateChallengeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final CreateChallengeUseCase createChallengeUseCase;

    public ChallengeController(CreateChallengeUseCase createChallengeUseCase) {
        this.createChallengeUseCase = createChallengeUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateChallengeResponse create(@Valid @RequestBody CreateChallengeRequest request) {
        var result = createChallengeUseCase.create(request.toCommand());
        return new CreateChallengeResponse(result.id().toString());
    }
}