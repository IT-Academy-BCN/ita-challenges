package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserSerializationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest
    @EnumSource(Role.class)
    void should_serialize_user_response_for_each_role(Role role) throws Exception {
        UserResponse response = new UserResponse("natasha", role);

        String json = mapper.writeValueAsString(response);

        assertThat(json)
                .contains("\"username\":\"john\"")
                .contains("\"role\":\"" + role.name() + "\"");
    }
}
