package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenge/submissions")
public class SubmissionController {

    private final SubmissionRepository submissionRepository;

    public SubmissionController(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @PostMapping("/draft")
    public ResponseEntity<Void> saveDraft(@RequestBody SaveDraftSubmissionRequest request)
    {

        Submission submission = Submission.createInProgress(
                SubmissionId.generate(),
                ChallengeId.of(request.challengeId()),
                UserId.of(request.userId()),
                request.code()
        );
        submissionRepository.save(submission);
        return ResponseEntity.status(201).build();
    }
    // TODO: POST /finalize    -> finalize submission
    // TODO: POST /incomplete  -> mark submission as incomplete
}
