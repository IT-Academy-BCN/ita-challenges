package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.FinalizeSubmissionRequest;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge/submissions")
public class SubmissionController {

    private final SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;
    private final FinalizeSubmissionUseCase finalizeSubmissionUseCase;

    public SubmissionController(SaveDraftSubmissionUseCase saveDraftSubmissionUseCase,
                                FinalizeSubmissionUseCase finalizeSubmissionUseCase) {
        this.saveDraftSubmissionUseCase = saveDraftSubmissionUseCase;
        this.finalizeSubmissionUseCase = finalizeSubmissionUseCase;
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

    @PostMapping("/finalize")
    public ResponseEntity<Void> finalize(@RequestBody FinalizeSubmissionRequest request) {
        try {
            finalizeSubmissionUseCase.execute(new FinalizeSubmissionCommand(
                    request.userId(),
                    request.challengeId(),
                    request.code()
            ));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}