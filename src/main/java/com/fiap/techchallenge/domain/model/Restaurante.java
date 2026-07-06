package com.fiap.techchallenge.domain.model;

public record Restaurante(
        Long id,
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        Long donoId,
        Long enderecoId
) {
}

