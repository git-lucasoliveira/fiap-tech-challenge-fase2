package com.fiap.techchallenge.application.dto;

public record CreateUserCommand(
        String nome,
        String email,
        String login,
        String senha,
        Long fkTipoUsuario,
        Long enderecoId
) {
}

