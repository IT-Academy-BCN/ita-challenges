package com.itachallenges.challengeservice.catalog.application.mapper;

import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.catalog.application.dto.UpdateChallengeCommand;
import com.itachallenges.challengeservice.catalog.domain.Challenge;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto.ChallengeRequest;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto.ChallengeResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ChallengeWebMapper {

    CreateChallengeCommand toCreateCommand(ChallengeRequest request);

    UpdateChallengeCommand toUpdateCommand(ChallengeRequest request, UUID id);

    default ChallengeResponse toResponse(Challenge challenge) {
        return new ChallengeResponse(
                challenge.id().toString(),
                challenge.title(),
                challenge.description()
        );
    }
}
