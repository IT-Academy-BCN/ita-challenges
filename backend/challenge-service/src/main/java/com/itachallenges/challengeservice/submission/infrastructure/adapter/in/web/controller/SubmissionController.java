package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.MarkIncompleteUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // TODO: POST /draft       -> save draft submission
    // TODO: POST /finalize    -> finalize submission
    // TODO: POST /incomplete  -> mark submission as incomplete
}
