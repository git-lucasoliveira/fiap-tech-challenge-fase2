package com.fiap.techchallenge.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRestauranteRequest(
        @NotBlank String nome,
        @NotBlank String tipoCozinha,
        String horarioFuncionamento,
        @NotNull Long donoId,
        Long enderecoId
) {
}

