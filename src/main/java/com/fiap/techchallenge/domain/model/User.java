package com.fiap.techchallenge.domain.model;

import java.time.LocalDateTime;

public record User(
        Long id,
        String nome,
        String email,
        String login,
        String senha,
        Long fkTipoUsuario,
        Long enderecoId,
        LocalDateTime dataUltimaAlteracao
) {
}

