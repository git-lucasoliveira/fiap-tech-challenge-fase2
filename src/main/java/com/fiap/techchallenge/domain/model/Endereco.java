package com.fiap.techchallenge.domain.model;

public record Endereco(
        Long id,
        String rua,
        String numero,
        String cidade,
        String cep,
        String complemento,
        String estado,
        String bairro
) {
}

