package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeResponse;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> findAllChallenges() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(@RequestBody ChallengeRequest request) {
        Challenge challenge = Challenge.create(request.title(), request.description());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ChallengeResponse(
                        challenge.getId().toString(),
                        challenge.getTitle().toString(),
                        challenge.getDescription().toString()
                )
        );
    }
}
