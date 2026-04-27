package com.itachallenges.challengeservice.infrastructure.adapter.web.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.controller.ChallengeController;
import com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.in.dto.ChallengeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    ChallengeRequest request = new ChallengeRequest(
            "Clean Code Challenge",
            "A challenge about writing clean and maintainable code"
    );

    @Test
    void should_return_201_with_challenge_when_valid_request() throws Exception {
        mockMvc.perform(post("/challenge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Clean Code Challenge"))
                .andExpect(jsonPath("$.description").value("A challenge about writing clean and maintainable code"));
    }


    @Test
    void should_return_204_when_delete_challenge_by_id() throws Exception {
        mockMvc.perform(delete("/api/challenge/{id}",  "dcacb291-ea40-4924-8430-6d4ef63908f2"))
                .andExpect(status().isNoContent());
    }
}