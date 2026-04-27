package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeResponse;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final ChallengeRepository challengeRepository;

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(@RequestBody ChallengeRequest request) {
        Challenge challenge = Challenge.create(request.title(), request.description());
        challengeRepository.save(challenge);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ChallengeResponse(
                        challenge.getId().toString(),
                        challenge.getTitle().toString(),
                        challenge.getDescription().toString()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> findAll() {
        List<ChallengeResponse> challenges = challengeRepository.findAll()
                .stream()
                .map(c -> new ChallengeResponse(c.getId().toString(), c.getTitle().toString(), c.getDescription().toString()))
                .toList();
        return ResponseEntity.ok(challenges);
    }
}