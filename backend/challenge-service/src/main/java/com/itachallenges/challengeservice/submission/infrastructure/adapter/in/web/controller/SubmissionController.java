package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/challenge/submissions")
public class SubmissionController {

    private final SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;
    private final SubmissionRepository submissionRepository;

    public SubmissionController(SaveDraftSubmissionUseCase saveDraftSubmissionUseCase,
                                SubmissionRepository submissionRepository) {
        this.saveDraftSubmissionUseCase = saveDraftSubmissionUseCase;
        this.submissionRepository = submissionRepository;
    }

    @PostMapping
    public ResponseEntity<Void> submit(@RequestBody SaveDraftSubmissionRequest request) {
        String code = request.code() == null ? "" : request.code();
        saveDraftSubmissionUseCase.execute(new SaveDraftSubmissionCommand(
                request.challengeId(),
                request.userId(),
                code
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getSubmission(@PathVariable String id) {
        return submissionRepository.findById(SubmissionId.of(id))
                .map(s -> ResponseEntity.ok(Map.of(
                        "id", s.getId().toString(),
                        "challengeId", s.getChallengeId().toString(),
                        "userId", s.getUserId().toString(),
                        "status", s.getStatus().toString(),
                        "code", s.getCode() == null ? "" : s.getCode()
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}