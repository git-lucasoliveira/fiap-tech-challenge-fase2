package com.fiap.techchallenge.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record TipoUsuarioRequest(@NotBlank String nome) {
}

