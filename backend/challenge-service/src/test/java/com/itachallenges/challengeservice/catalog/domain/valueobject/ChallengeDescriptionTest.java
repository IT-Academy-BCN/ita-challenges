package com.itachallenges.challengeservice.catalog.domain.valueobject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChallengeDescriptionTest {

    @Test
    void should_create_description_with_valid_value() {
        ChallengeDescription description = new ChallengeDescription("A valid description");

        assertThat(description.value()).isEqualTo("A valid description");
    }

    @Test
    void should_throw_exception_when_description_is_null() {
        assertThatThrownBy(() -> new ChallengeDescription(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_exception_when_description_is_empty() {
        assertThatThrownBy(() -> new ChallengeDescription(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_exception_when_description_is_blank() {
        assertThatThrownBy(() -> new ChallengeDescription("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }
}