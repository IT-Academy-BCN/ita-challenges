package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeResponse;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class ChallengeController {

    private final ChallengeRepository repository;

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(@RequestBody ChallengeRequest request) {
        Challenge saved = repository.save(
                Challenge.create(request.title(), request.description())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ChallengeResponse(
                        saved.getId().toString(),
                        saved.getTitle().toString(),
                        saved.getDescription().toString()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> findAll() {
        List<ChallengeResponse> challenges = repository.findAll()
                .stream()
                .map(c -> new ChallengeResponse(c.getId().toString(), c.getTitle().toString(), c.getDescription().toString()))
                .toList();
        return ResponseEntity.ok(challenges);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponse> find(@PathVariable String id){
            Challenge challenge = repository.find(ChallengeId.of(id));

            ChallengeResponse challengeResponse =
                    new ChallengeResponse(challenge.getId().toString(),
                            challenge.getTitle().toString(),
                            challenge.getDescription().toString());
            return ResponseEntity.ok(challengeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repository.delete(ChallengeId.of(id));
        return ResponseEntity.noContent().build();
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
