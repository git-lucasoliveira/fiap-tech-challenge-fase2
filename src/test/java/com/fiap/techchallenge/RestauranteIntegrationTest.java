package com.fiap.techchallenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.domain.repository.UserRepository;
import com.fiap.techchallenge.presentation.dto.CreateRestauranteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class RestauranteIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastraRestauranteComDonoExistente() throws Exception {
        User dono = userRepository.save(new User(null, "Ana", "ana@email.com", "ana",
                "123", 1L, null, LocalDateTime.now()));

        CreateRestauranteRequest request = new CreateRestauranteRequest(
                "Cantina", "Italiana", "10h-22h", dono.id(), null);

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Cantina"))
                .andExpect(jsonPath("$.donoId").value(dono.id()));
    }

    @Test
    void naoCadastraRestauranteComDonoInexistente() throws Exception {
        CreateRestauranteRequest request = new CreateRestauranteRequest(
                "Cantina", "Italiana", "10h-22h", 999999L, null);

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
