package com.fiap.techchallenge.presentation.dto;

public record EnderecoResponse(
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

