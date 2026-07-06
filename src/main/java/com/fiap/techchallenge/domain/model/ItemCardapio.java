package com.fiap.techchallenge.domain.model;

import java.math.BigDecimal;

public record ItemCardapio(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivelLocal,
        String caminhoFoto,
        Long restauranteId
) {
}

