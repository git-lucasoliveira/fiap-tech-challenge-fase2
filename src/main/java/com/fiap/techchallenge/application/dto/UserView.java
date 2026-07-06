package com.fiap.techchallenge.application.dto;

import java.time.LocalDateTime;

public record UserView(
        Long id,
        String nome,
        String email,
        String login,
        Long fkTipoUsuario,
        Long enderecoId,
        LocalDateTime dataUltimaAlteracao
) {
}

