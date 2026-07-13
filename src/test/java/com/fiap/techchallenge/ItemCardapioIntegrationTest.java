package com.fiap.techchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemCardapioIntegrationTest extends IntegrationTestBase {

    @Test
    void deveExecutarOCrudCompletoDeItemCardapio() throws Exception {
        long restauranteId = criarCadeiaAteRestaurante("Dono Item Crud", "item.crud", "Restaurante Item Crud");

        String payload = """
                {
                  "nome": "Pizza Margherita",
                  "descricao": "Molho de tomate e manjericao",
                  "preco": 39.90,
                  "disponivelLocal": true,
                  "caminhoFoto": "/fotos/pizza.png",
                  "restauranteId": %d
                }
                """.formatted(restauranteId);

        String body = mockMvc.perform(post("/api/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$.restauranteId").value(restauranteId))
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(body).get("id").asLong();

        mockMvc.perform(get("/api/itens-cardapio/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Molho de tomate e manjericao"));

        String atualizado = """
                {
                  "nome": "Pizza Calabresa",
                  "descricao": "Calabresa e cebola",
                  "preco": 42.00,
                  "disponivelLocal": false,
                  "caminhoFoto": "/fotos/calabresa.png"
                }
                """;

        mockMvc.perform(put("/api/itens-cardapio/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atualizado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pizza Calabresa"))
                .andExpect(jsonPath("$.restauranteId").value(restauranteId));

        mockMvc.perform(delete("/api/itens-cardapio/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/itens-cardapio/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveFiltrarItensPorRestaurante() throws Exception {
        long restauranteId = criarCadeiaAteRestaurante("Dono Item Filtro", "item.filtro", "Restaurante Item Filtro");

        String payload = """
                {
                  "nome": "Refrigerante",
                  "descricao": "Lata 350ml",
                  "preco": 6.50,
                  "disponivelLocal": true,
                  "caminhoFoto": "/fotos/refri.png",
                  "restauranteId": %d
                }
                """.formatted(restauranteId);

        mockMvc.perform(post("/api/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/itens-cardapio").param("restauranteId", String.valueOf(restauranteId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restauranteId").value(restauranteId));
    }

    @Test
    void deveRetornar404AoCadastrarItemComRestauranteInexistente() throws Exception {
        String payload = """
                {
                  "nome": "Item Solto",
                  "descricao": "Sem restaurante",
                  "preco": 10.00,
                  "disponivelLocal": true,
                  "caminhoFoto": "/fotos/item.png",
                  "restauranteId": 999999
                }
                """;

        mockMvc.perform(post("/api/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400AoCadastrarItemComPrecoInvalido() throws Exception {
        long restauranteId = criarCadeiaAteRestaurante("Dono Item Validacao", "item.validacao", "Restaurante Item Validacao");

        String payload = """
                {
                  "nome": "Item Gratis",
                  "descricao": "Preco invalido",
                  "preco": 0.00,
                  "disponivelLocal": true,
                  "caminhoFoto": "/fotos/item.png",
                  "restauranteId": %d
                }
                """.formatted(restauranteId);

        mockMvc.perform(post("/api/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    private long criarCadeiaAteRestaurante(String nomeTipo, String loginDono, String nomeRestaurante) throws Exception {
        long tipoId = criarTipoUsuario(nomeTipo);
        long donoId = criarUsuario("Dono", loginDono + "@fiap.com", loginDono, tipoId);
        return criarRestaurante(nomeRestaurante, donoId);
    }
}
