package com.fiap.techchallenge.application.dto;

import java.math.BigDecimal;

public record ItemCardapioView(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivelLocal,
        String caminhoFoto,
        Long restauranteId
) {
}

