package com.fiap.techchallenge.application.dto;

public record CreateRestauranteCommand(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        Long donoId,
        Long enderecoId
) {
}

