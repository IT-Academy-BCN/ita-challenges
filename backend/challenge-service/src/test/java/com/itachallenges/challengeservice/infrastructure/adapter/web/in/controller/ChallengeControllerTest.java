package com.itachallenges.challengeservice.infrastructure.adapter.web.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDescription;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeTitle;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller.ChallengeController;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeRequest;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChallengeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    ChallengeRequest request = new ChallengeRequest(
            "Clean Code Challenge",
            "A challenge about writing clean and maintainable code",
            ChallengeLanguage.JAVA,
            null
    );

    @Test
    void should_return_200_with_empty_list_when_requesting_all_challenges() throws Exception {
        when(repository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/challenge"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void should_create_challenge_with_title_and_description_and_return_201() throws Exception {
        Challenge saved = Challenge.create(request.title(), request.description(), ChallengeLanguage.JAVA, ChallengeDifficulty.EASY);
        when(repository.save(any(Challenge.class))).thenReturn(saved);

        mockMvc.perform(post("/api/challenge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Clean Code Challenge"))
                .andExpect(jsonPath("$.description").value("A challenge about writing clean and maintainable code"))
                .andExpect(jsonPath("$.language").value("JAVA"));
    }

    @Test
    void should_return_204_when_delete_challenge_by_id() throws Exception {
        String challengeIdStr = UUID.randomUUID().toString();

        mockMvc.perform(delete("/api/challenge/{id}", challengeIdStr))
                .andExpect(status().isNoContent());

        verify(repository).delete(any(ChallengeId.class));
    }

    @Test
    void should_update_challenge_and_return_200() throws Exception {
        String id = UUID.randomUUID().toString();
        ChallengeRequest request = new ChallengeRequest(
                "Updated title",
                "Updated description",
                ChallengeLanguage.JAVA,
                null
        );

        Challenge updatedChallenge = Challenge.restore(
                new ChallengeId(UUID.fromString(id)),
                "Updated title",
                "Updated description",
                ChallengeLanguage.JAVA,
                ChallengeDifficulty.EASY
        );

        when(repository.update(any(Challenge.class)))
                .thenReturn(updatedChallenge);

        mockMvc.perform(put("/api/challenge/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.language").value("JAVA"));
    }


    @Test
    void should_return_200_with_challenge_when_finding_by_id() throws Exception {
        UUID uuid = UUID.randomUUID();
        ChallengeId challengeId = new ChallengeId(uuid);

        Challenge challenge = org.mockito.Mockito.mock(Challenge.class);
        when(challenge.getId()).thenReturn(challengeId);
        when(challenge.getTitle()).thenReturn(new ChallengeTitle("Test Title"));
        when(challenge.getDescription()).thenReturn(new ChallengeDescription("Test Description"));
        when(challenge.getLanguage()).thenReturn(ChallengeLanguage.JAVA);

        when(repository.find(challengeId)).thenReturn(challenge);

        mockMvc.perform(get("/api/challenge/{id}", uuid.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(uuid.toString()))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }
}