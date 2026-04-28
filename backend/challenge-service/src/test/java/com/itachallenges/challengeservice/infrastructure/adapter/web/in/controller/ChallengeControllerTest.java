package com.itachallenges.challengeservice.infrastructure.adapter.web.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller.ChallengeController;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeRequest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
            "A challenge about writing clean and maintainable code"
    );

    @Test
    void should_return_200_with_empty_list_when_requesting_all_challenges() throws Exception {
        mockMvc.perform(get("/api/challenge"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void should_return_201_with_challenge_when_valid_request() throws Exception {
        mockMvc.perform(post("/api/challenge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Clean Code Challenge"))
                .andExpect(jsonPath("$.description").value("A challenge about writing clean and maintainable code"));
    }

    @Test
    void should_update_challenge_and_return_200() throws Exception {

        String id = UUID.randomUUID().toString();

        ChallengeRequest request = new ChallengeRequest(
                "Updated title",
                "Updated description"
        );

        Challenge updatedChallenge = Challenge.restore(
                new ChallengeId(UUID.fromString(id)),
                "Updated title",
                "Updated description"
        );

        when(repository.update(org.mockito.ArgumentMatchers.any(Challenge.class)))
                .thenReturn(updatedChallenge);

        mockMvc.perform(put("/api/challenge/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.description").value("Updated description"));
    }
}
