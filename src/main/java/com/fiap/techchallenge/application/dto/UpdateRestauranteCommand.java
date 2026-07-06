package com.fiap.techchallenge.application.dto;

public record UpdateRestauranteCommand(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        Long donoId,
        Long enderecoId
) {
}

