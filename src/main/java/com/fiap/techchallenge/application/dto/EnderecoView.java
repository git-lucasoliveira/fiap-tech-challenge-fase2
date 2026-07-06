package com.fiap.techchallenge.application.dto;

public record EnderecoView(
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

