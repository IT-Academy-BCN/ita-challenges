package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
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
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SubmissionResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;

@RestController
@RequestMapping("/api/challenge/submissions")
public class SubmissionController {

    private final SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;
    private final SubmissionRepository submissionRepository;
    private final ChallengeRepository challengeRepository;

    public SubmissionController(SaveDraftSubmissionUseCase saveDraftSubmissionUseCase, SubmissionRepository submissionRepository, ChallengeRepository challengeRepository) {
        this.saveDraftSubmissionUseCase = saveDraftSubmissionUseCase;
        this.submissionRepository = submissionRepository;
        this.challengeRepository = challengeRepository;
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
    public ResponseEntity<SubmissionResultResponse> finalize(@RequestBody FinalizeSubmissionRequest request){
        Submission finalSubmission = Submission.createSubmitted(
                SubmissionId.generate(),
                ChallengeId.of(request.challengeId()),
                UserId.of(request.userId()),
                request.code()
        );
        submissionRepository.save(finalSubmission);

        Challenge challenge = challengeRepository.find(ChallengeId.of(request.challengeId()));

        String officialSolution = request.revealOfficialSolution()
                ? challenge.getSolution().toString()
                : null;

        SubmissionResultResponse response = new SubmissionResultResponse(
                finalSubmission.getId().toString(),
                request.code(),
                challenge.getTitle().toString(),
                officialSolution,
                request.revealOfficialSolution()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/finalized")
    public ResponseEntity<ExistsFinalSubmissionResponse> existsFinalSubmission(
            @RequestParam String userId,
            @RequestParam String challengeId
    ) {
        boolean exists = submissionRepository.existsFinalSubmission(UserId.of(userId), ChallengeId.of(challengeId));
        return ResponseEntity.status(HttpStatus.OK).body(new ExistsFinalSubmissionResponse(exists));
    }
}
