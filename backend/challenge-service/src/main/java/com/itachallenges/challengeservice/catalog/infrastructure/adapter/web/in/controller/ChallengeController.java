package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeResponse;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final ChallengeRepository repository;

    public ChallengeController(ChallengeRepository repository) {
        this.repository = repository;
    }
    
      @GetMapping
    public ResponseEntity<List<ChallengeResponse>> findAll() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(@RequestBody ChallengeRequest request) {
        Challenge challenge = Challenge.create(request.title(), request.description());

        repository.update(challenge);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ChallengeResponse(
                        challenge.getId().toString(),
                        challenge.getTitle().toString(),
                        challenge.getDescription().toString()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeResponse> update(
            @PathVariable String id,
            @RequestBody ChallengeRequest request
    ) {

        Challenge challenge = Challenge.restore(
                new ChallengeId(UUID.fromString(id)),
                request.title(),
                request.description()
        );

        Challenge updated = repository.update(challenge);

        ChallengeResponse response = new ChallengeResponse(
                updated.getId().toString(),
                updated.getTitle().toString(),
                updated.getDescription().toString()
        );

        return ResponseEntity.ok(response);
    }
}

