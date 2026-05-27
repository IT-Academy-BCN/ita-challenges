package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.ExistsFinalSubmissionResponse;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.FinalizeSubmissionRequest;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/challenge/submissions")
public class SubmissionController {

    private final SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;
    private final SubmissionRepository repository;

    public SubmissionController(SaveDraftSubmissionUseCase saveDraftSubmissionUseCase, SubmissionRepository repository) {
        this.saveDraftSubmissionUseCase = saveDraftSubmissionUseCase;
        this.repository = repository;
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

    @PostMapping("/submit")
    public ResponseEntity<Void> finalize(@RequestBody FinalizeSubmissionRequest request) {
        Submission finalSubmission = Submission.createSubmitted(
                SubmissionId.generate(),
                ChallengeId.of(request.challengeId()),
                UserId.of(request.userId()),
                request.code()
        );
        repository.save(finalSubmission);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/finalized")
    public ResponseEntity<ExistsFinalSubmissionResponse> existsFinalSubmission(
            @RequestParam String userId,
            @RequestParam String challengeId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(new ExistsFinalSubmissionResponse(false));
    }
}
