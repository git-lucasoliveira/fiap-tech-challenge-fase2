package com.fiap.techchallenge.presentation.dto;

public record EnderecoRequest(
        String rua,
        String numero,
        String cidade,
        String cep,
        String complemento,
        String estado,
        String bairro
) {
}

