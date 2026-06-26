package com.itachallenges.challengeservice.infrastructure.adapter.web.in.controller;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller.ChallengeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChallengeController.class)
class ChallengeControllerFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChallengeRepository repository;

    @Test
    void should_return_200_with_filtered_list_when_requesting_challenges_by_language() throws Exception {
        Challenge javaChallenge = Challenge.create(
                "Java Challenge", "Java description",
                ChallengeLanguage.JAVA, ChallengeDifficulty.EASY, "Java solution"
        );
        when(repository.findByCriteria(ChallengeLanguage.JAVA, null)).thenReturn(List.of(javaChallenge));

        mockMvc.perform(get("/api/challenge").param("language", "JAVA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Java Challenge"))
                .andExpect(jsonPath("$[0].language").value("JAVA"));
    }

    @Test
    void should_return_400_when_requesting_challenges_with_invalid_language() throws Exception {
        mockMvc.perform(get("/api/challenge").param("language", "COBOL"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_200_with_empty_list_when_filter_matches_no_challenges() throws Exception {
        when(repository.findByCriteria(ChallengeLanguage.SQL, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/challenge").param("language", "SQL"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
