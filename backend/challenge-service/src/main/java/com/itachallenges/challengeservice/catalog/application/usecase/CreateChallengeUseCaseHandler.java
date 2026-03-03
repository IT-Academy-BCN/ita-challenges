package com.itachallenges.challengeservice.catalog.application.usecase;

import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeResponse;
import com.itachallenges.challengeservice.catalog.domain.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.in.CreateChallengeUseCase;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateChallengeUseCaseHandler implements CreateChallengeUseCase {

    private final ChallengeRepository challengeRepository;

    public CreateChallengeUseCaseHandler(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public CreateChallengeResponse create(CreateChallengeCommand command) {

        Challenge challenge = Challenge.createNew(
                command.title(),
                command.description()
        );

        Challenge saved = challengeRepository.save(challenge);

        return new CreateChallengeResponse(saved.id());
    }
}
