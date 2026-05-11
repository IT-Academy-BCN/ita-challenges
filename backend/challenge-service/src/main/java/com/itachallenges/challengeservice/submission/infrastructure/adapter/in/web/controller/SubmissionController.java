package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.MarkIncompleteUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.FinalizeSubmissionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    private final FinalizeSubmissionUseCase finalizeSubmissionUseCase;
    private final MarkIncompleteUseCase markIncompleteUseCase;
    private final SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;

    public SubmissionController(FinalizeSubmissionUseCase finalizeSubmissionUseCase,
                                MarkIncompleteUseCase markIncompleteUseCase,
                                SaveDraftSubmissionUseCase saveDraftSubmissionUseCase) {
        this.finalizeSubmissionUseCase = finalizeSubmissionUseCase;
        this.markIncompleteUseCase = markIncompleteUseCase;
        this.saveDraftSubmissionUseCase = saveDraftSubmissionUseCase;
    }

    @PostMapping("/finalize")
    public ResponseEntity<Void> finalize(@RequestBody FinalizeSubmissionRequest request) {
        try {
            List<String> content = request.content() == null ? List.of() : request.content();
            FinalizeSubmissionCommand command = new FinalizeSubmissionCommand(
                    request.challengeId(),
                    request.userId(),
                    content
            );
            finalizeSubmissionUseCase.execute(command);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
