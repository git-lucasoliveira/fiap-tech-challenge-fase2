package com.fiap.techchallenge.application.dto;

public record RestauranteView(
        Long id,
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        Long donoId,
        Long enderecoId
) {
}

