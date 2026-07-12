package com.fiap.techchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TipoUsuarioIntegrationTest extends IntegrationTestBase {

    @Test
    void deveExecutarOCrudCompletoDeTipoUsuario() throws Exception {
        String body = mockMvc.perform(post("/api/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Gerente\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Gerente"))
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(body).get("id").asLong();

        mockMvc.perform(get("/api/tipos-usuario/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gerente"));

        mockMvc.perform(get("/api/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));

        mockMvc.perform(put("/api/tipos-usuario/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Supervisor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Supervisor"));

        mockMvc.perform(delete("/api/tipos-usuario/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/tipos-usuario/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoBuscarTipoUsuarioInexistente() throws Exception {
        mockMvc.perform(get("/api/tipos-usuario/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400AoCriarTipoUsuarioComNomeEmBranco() throws Exception {
        mockMvc.perform(post("/api/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
