package com.fiap.techchallenge.application.dto;

public record EnderecoCommand(
        String rua,
        String numero,
        String cidade,
        String cep,
        String complemento,
        String estado,
        String bairro
) {
}

