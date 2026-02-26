package com.itachallenges.challengeservice.catalog.application.usecase;

import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.catalog.domain.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateChallengeUseCaseHandlerTest {

    private final ChallengeRepository repository = mock(ChallengeRepository.class);
    private final CreateChallengeUseCaseHandler service = new CreateChallengeUseCaseHandler(repository);

    @Test
    void shouldCreateChallengeAndReturnId() {
        when(repository.save(any(Challenge.class)))
                .thenAnswer(inv -> inv.getArgument(0, Challenge.class));

        var result = service.create(new CreateChallengeCommand("T1", "D1"));

        assertNotNull(result.id());
        verify(repository).save(any(Challenge.class));
        verifyNoMoreInteractions(repository);
    }
}
