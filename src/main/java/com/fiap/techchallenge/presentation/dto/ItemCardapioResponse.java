package com.fiap.techchallenge.presentation.dto;

import java.math.BigDecimal;

public record ItemCardapioResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivelLocal,
        String caminhoFoto,
        Long restauranteId
) {
}

