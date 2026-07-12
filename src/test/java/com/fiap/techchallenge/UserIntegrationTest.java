package com.fiap.techchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserIntegrationTest extends IntegrationTestBase {

    @Test
    void deveCadastrarUsuarioAssociadoAUmTipoUsuario() throws Exception {
        long tipoId = criarTipoUsuario("Cliente Padrao");

        String payload = """
                {
                  "nome": "Maria Silva",
                  "email": "maria@fiap.com",
                  "login": "maria.silva",
                  "senha": "senhaSegura123",
                  "fkTipoUsuario": %d
                }
                """.formatted(tipoId);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Maria Silva"))
                .andExpect(jsonPath("$.fkTipoUsuario").value(tipoId));
    }

    @Test
    void deveExecutarOCrudCompletoDeUsuario() throws Exception {
        long tipoId = criarTipoUsuario("Cliente Crud");
        long usuarioId = criarUsuario("Joao", "joao@fiap.com", "joao.crud", tipoId);

        mockMvc.perform(get("/api/users/{id}", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("joao.crud"));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());

        String payload = """
                {
                  "nome": "Joao Atualizado",
                  "email": "joao.novo@fiap.com",
                  "login": "joao.crud",
                  "senha": "outraSenha123",
                  "fkTipoUsuario": %d
                }
                """.formatted(tipoId);

        mockMvc.perform(put("/api/users/{id}", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Joao Atualizado"))
                .andExpect(jsonPath("$.email").value("joao.novo@fiap.com"));

        mockMvc.perform(delete("/api/users/{id}", usuarioId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{id}", usuarioId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoCadastrarUsuarioComTipoInexistente() throws Exception {
        String payload = """
                {
                  "nome": "Pedro",
                  "email": "pedro@fiap.com",
                  "login": "pedro",
                  "senha": "senha123",
                  "fkTipoUsuario": 999999
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400AoCadastrarUsuarioComEmailInvalido() throws Exception {
        long tipoId = criarTipoUsuario("Cliente Validacao");

        String payload = """
                {
                  "nome": "Ana",
                  "email": "email-invalido",
                  "login": "ana.validacao",
                  "senha": "senha123",
                  "fkTipoUsuario": %d
                }
                """.formatted(tipoId);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }
}
