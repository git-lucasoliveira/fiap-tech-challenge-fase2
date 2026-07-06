package com.fiap.techchallenge.presentation.dto;

public record RestauranteResponse(
        Long id,
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        Long donoId,
        Long enderecoId
) {
}

