package com.itachallenges.challengeservice.catalog.application.mapper;

import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.catalog.application.dto.UpdateChallengeCommand;
import com.itachallenges.challengeservice.catalog.domain.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto.ChallengeRequest;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.dto.ChallengeResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ChallengeWebMapperTest {
    private static final String TITLE = "Clean Code";
    private static final String DESCRIPTION = "Write readable code";

    private final ChallengeWebMapper mapper = Mappers.getMapper(ChallengeWebMapper.class);

    private static Challenge aChallenge(ChallengeId id) {
        return Challenge.restore(id, TITLE, DESCRIPTION);
    }

    private static ChallengeRequest aRequest() {
        return new ChallengeRequest(TITLE, DESCRIPTION);
    }


    @Test
    void toResponse_shouldUnwrapChallengeId() {
        ChallengeId id = ChallengeId.generate();

        ChallengeResponse response = mapper.toResponse(aChallenge(id));

        assertThat(response.id()).isEqualTo(id.toString());
    }

    @Test
    void toResponse_shouldMapAllFields() {
        ChallengeResponse response = mapper.toResponse(aChallenge(ChallengeId.generate()));

        assertThat(response.title()).isEqualTo(TITLE);
        assertThat(response.description()).isEqualTo(DESCRIPTION);
    }


    @Test
    void toCreateCommand_shouldMapTitleAndDescription() {
        CreateChallengeCommand command = mapper.toCreateCommand(aRequest());

        assertThat(command.title()).isEqualTo(TITLE);
        assertThat(command.description()).isEqualTo(DESCRIPTION);
    }


    @Test
    void toUpdateCommand_shouldMapRequestFieldsAndPathId() {
        UUID id = UUID.randomUUID();

        UpdateChallengeCommand command = mapper.toUpdateCommand(aRequest(), id);

        assertThat(command.id()).isEqualTo(id);
        assertThat(command.title()).isEqualTo(TITLE);
        assertThat(command.description()).isEqualTo(DESCRIPTION);
    }
}