package com.itachallenges.challengeservice.application.service;

import com.itachallenges.challengeservice.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.domain.model.Challenge;
import com.itachallenges.challengeservice.domain.port.out.ChallengeRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateChallengeServiceTest {

    private final ChallengeRepository repository = mock(ChallengeRepository.class);
    private final CreateChallengeService service = new CreateChallengeService(repository);

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
