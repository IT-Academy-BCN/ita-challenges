package com.itachallenges.challengeservice.catalog.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChallengeSolutionTest {

    @Test
    void should_create_solution_with_valid_value() {
        ChallengeSolution solution = new ChallengeSolution("System.out.println();");
        assertThat(solution.value()).isEqualTo("System.out.println();");
    }

    @Test
    void should_throw_exception_when_solution_is_null() {
        assertThatThrownBy(() -> new ChallengeSolution(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_exception_when_solution_is_empty() {
        assertThatThrownBy(() -> new ChallengeSolution(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_exception_when_solution_is_blank() {
        assertThatThrownBy(() -> new ChallengeSolution("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }
}