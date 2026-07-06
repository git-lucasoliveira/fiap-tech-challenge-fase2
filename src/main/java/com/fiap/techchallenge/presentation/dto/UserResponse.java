package com.fiap.techchallenge.presentation.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String nome,
        String email,
        String login,
        Long fkTipoUsuario,
        Long enderecoId,
        LocalDateTime dataUltimaAlteracao
) {
}

