package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.MarkIncompleteUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping
    public ResponseEntity<Void> submit(@RequestBody SaveDraftSubmissionRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        if (isBlank(request.challengeId())) {
            return ResponseEntity.badRequest().build();
        }

        if (isBlank(request.userId())) {
            return ResponseEntity.badRequest().build();
        }

        List<String> content = request.content() == null ? List.of() : request.content();

        SaveDraftSubmissionCommand command = new SaveDraftSubmissionCommand(
                request.challengeId(),
                request.userId(),
                content
        );

        saveDraftSubmissionUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
