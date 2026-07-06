package com.fiap.techchallenge.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateItemCardapioRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull @DecimalMin("0.01") BigDecimal preco,
        Boolean disponivelLocal,
        String caminhoFoto,
        @NotNull Long restauranteId
) {
}

