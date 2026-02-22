package com.itachallenges.challengeservice.application.service;

import com.itachallenges.challengeservice.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.application.dto.CreateChallengeResult;
import com.itachallenges.challengeservice.domain.model.Challenge;
import com.itachallenges.challengeservice.domain.port.in.CreateChallengeUseCase;
import com.itachallenges.challengeservice.domain.port.out.ChallengeRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateChallengeService implements CreateChallengeUseCase {

    private final ChallengeRepository challengeRepository;

    public CreateChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public CreateChallengeResult create(CreateChallengeCommand command) {

        Challenge challenge = Challenge.createNew(
                command.title(),
                command.description()
        );

        Challenge saved = challengeRepository.save(challenge);

        return new CreateChallengeResult(saved.id());
    }
}
