package com.fiap.techchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EnderecoIntegrationTest extends IntegrationTestBase {

    @Test
    void deveExecutarOCrudCompletoDeEndereco() throws Exception {
        String payload = """
                {
                  "rua": "Rua das Flores",
                  "numero": "100",
                  "cidade": "Sao Paulo",
                  "cep": "01000-000",
                  "complemento": "Apto 12",
                  "estado": "SP",
                  "bairro": "Centro"
                }
                """;

        String body = mockMvc.perform(post("/api/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cidade").value("Sao Paulo"))
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(body).get("id").asLong();

        mockMvc.perform(get("/api/enderecos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rua").value("Rua das Flores"));

        mockMvc.perform(get("/api/enderecos"))
                .andExpect(status().isOk());

        String atualizado = """
                {
                  "rua": "Avenida Brasil",
                  "numero": "200",
                  "cidade": "Rio de Janeiro",
                  "cep": "20000-000",
                  "complemento": "Casa",
                  "estado": "RJ",
                  "bairro": "Copacabana"
                }
                """;

        mockMvc.perform(put("/api/enderecos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atualizado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("Rio de Janeiro"));

        mockMvc.perform(delete("/api/enderecos/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/enderecos/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoBuscarEnderecoInexistente() throws Exception {
        mockMvc.perform(get("/api/enderecos/{id}", 999999))
                .andExpect(status().isNotFound());
    }
}
