package com.fiap.techchallenge.application.dto;

import java.math.BigDecimal;

public record UpdateItemCardapioCommand(
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivelLocal,
        String caminhoFoto
) {
}

