package com.fiap.techchallenge.presentation.dto;

public record UpdateRestauranteRequest(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        Long donoId,
        Long enderecoId
) {
}

