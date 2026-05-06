package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserSerializationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest
    @EnumSource(Role.class)
    void should_deserialize_valid_request_for_each_role(Role role) throws Exception {
        String json = """
                {
                  "username": "john",
                  "role": "%s"
                }
                """.formatted(role.name());

        UserRequest result = mapper.readValue(json, UserRequest.class);

        assertThat(result.username()).isEqualTo("john");
        assertThat(result.role()).isEqualTo(role);
    }

    @Test
    void should_fail_when_role_is_unknown() {
        String json = """
                {
                  "username": "john",
                  "role": "SUPERUSER"
                }
                """;

        assertThatThrownBy(() -> mapper.readValue(json, UserRequest.class))
                .isInstanceOf(Exception.class);
    }
}
