package com.itachallenges.challengeservice.catalog.domain.valueobject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChallengeTitleTest {

    @Test
    void should_create_title_with_valid_value() {
        ChallengeTitle title = new ChallengeTitle("Clean Code Challenge");

        assertThat(title.value()).isEqualTo("Clean Code Challenge");
    }

    @Test
    void should_throw_exception_when_title_is_null() {
        assertThatThrownBy(() -> new ChallengeTitle(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_exception_when_title_is_empty() {
        assertThatThrownBy(() -> new ChallengeTitle(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_exception_when_title_is_blank() {
        assertThatThrownBy(() -> new ChallengeTitle("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
