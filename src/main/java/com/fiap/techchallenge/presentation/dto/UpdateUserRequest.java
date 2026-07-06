package com.fiap.techchallenge.presentation.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
        String nome,
        @Email String email,
        String login,
        String senha,
        Long fkTipoUsuario,
        Long enderecoId
) {
}

