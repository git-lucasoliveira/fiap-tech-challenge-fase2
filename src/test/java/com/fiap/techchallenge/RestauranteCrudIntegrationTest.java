package com.fiap.techchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestauranteCrudIntegrationTest extends IntegrationTestBase {

    @Test
    void deveExecutarOCrudCompletoDeRestaurante() throws Exception {
        long tipoId = criarTipoUsuario("Dono Restaurante Crud");
        long donoId = criarUsuario("Carlos", "carlos@fiap.com", "carlos.dono", tipoId);
        long restauranteId = criarRestaurante("Cantina do Carlos", donoId);

        mockMvc.perform(get("/api/restaurantes/{id}", restauranteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cantina do Carlos"))
                .andExpect(jsonPath("$.donoId").value(donoId));

        mockMvc.perform(get("/api/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(restauranteId));

        String atualizado = """
                {
                  "nome": "Cantina Nova",
                  "tipoCozinha": "Brasileira",
                  "horarioFuncionamento": "10:00-22:00",
                  "donoId": %d
                }
                """.formatted(donoId);

        mockMvc.perform(put("/api/restaurantes/{id}", restauranteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atualizado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cantina Nova"))
                .andExpect(jsonPath("$.tipoCozinha").value("Brasileira"));

        mockMvc.perform(delete("/api/restaurantes/{id}", restauranteId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/restaurantes/{id}", restauranteId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveFiltrarRestaurantesPorDono() throws Exception {
        long tipoId = criarTipoUsuario("Dono Restaurante Filtro");
        long donoId = criarUsuario("Rita", "rita@fiap.com", "rita.dono", tipoId);
        long restauranteId = criarRestaurante("Restaurante da Rita", donoId);

        mockMvc.perform(get("/api/restaurantes").param("donoId", String.valueOf(donoId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(restauranteId))
                .andExpect(jsonPath("$[0].donoId").value(donoId));
    }

    @Test
    void deveRetornar404AoAtualizarRestauranteComDonoInexistente() throws Exception {
        long tipoId = criarTipoUsuario("Dono Restaurante Update");
        long donoId = criarUsuario("Bruno", "bruno@fiap.com", "bruno.dono", tipoId);
        long restauranteId = criarRestaurante("Restaurante do Bruno", donoId);

        String payload = """
                {
                  "nome": "Restaurante do Bruno",
                  "tipoCozinha": "Japonesa",
                  "horarioFuncionamento": "18:00-23:00",
                  "donoId": 999999
                }
                """;

        mockMvc.perform(put("/api/restaurantes/{id}", restauranteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400AoCadastrarRestauranteSemNome() throws Exception {
        long tipoId = criarTipoUsuario("Dono Restaurante Validacao");
        long donoId = criarUsuario("Paula", "paula@fiap.com", "paula.dono", tipoId);

        String payload = """
                {
                  "nome": "",
                  "tipoCozinha": "Japonesa",
                  "horarioFuncionamento": "18:00-23:00",
                  "donoId": %d
                }
                """.formatted(donoId);

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }
}
