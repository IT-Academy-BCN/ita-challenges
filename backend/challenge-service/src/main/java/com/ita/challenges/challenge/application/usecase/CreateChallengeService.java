package com.ita.challenges.challenge.application.usecase;

import com.ita.challenges.challenge.application.dto.CreateChallengeCommand;
import com.ita.challenges.challenge.application.dto.CreateChallengeResult;
import com.ita.challenges.challenge.domain.model.Challenge;
import com.ita.challenges.challenge.domain.port.in.CreateChallengeUseCase;
import com.ita.challenges.challenge.domain.port.out.ChallengeRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateChallengeService implements CreateChallengeUseCase {

    private final ChallengeRepository challengeRepository;

    public CreateChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public CreateChallengeResult create(CreateChallengeCommand command) {

        Challenge challenge =
                Challenge.createNew(
                        command.title(),
                        command.description()
                );

        Challenge saved = challengeRepository.save(challenge);

        return new CreateChallengeResult(saved.id().toString());
    }
}