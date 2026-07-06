package com.fiap.techchallenge.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String login,
        @NotBlank String senha,
        @NotNull Long fkTipoUsuario,
        Long enderecoId
) {
}

