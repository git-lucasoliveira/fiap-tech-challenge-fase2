package com.fiap.techchallenge.presentation.dto;

import java.math.BigDecimal;

public record UpdateItemCardapioRequest(
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivelLocal,
        String caminhoFoto
) {
}

