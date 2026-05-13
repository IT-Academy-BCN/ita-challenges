package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/challenge/submissions")
public class SubmissionController {

    private final FinalizeSubmissionUseCase finalizeSubmissionUseCase;

    public SubmissionController(FinalizeSubmissionUseCase finalizeSubmissionUseCase) {
        this.finalizeSubmissionUseCase = finalizeSubmissionUseCase;
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
    private final SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;

    public SubmissionController(SaveDraftSubmissionUseCase saveDraftSubmissionUseCase) {
        this.saveDraftSubmissionUseCase = saveDraftSubmissionUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> submit(@RequestBody SaveDraftSubmissionRequest request) {
        String code = request.code() == null ? "" : request.code();

        SaveDraftSubmissionCommand command = new SaveDraftSubmissionCommand(
                request.challengeId(),
                request.userId(),
                code
        );

        saveDraftSubmissionUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
