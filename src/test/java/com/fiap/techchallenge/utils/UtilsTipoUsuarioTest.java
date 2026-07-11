package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.domain.model.TipoUsuario;

public abstract class UtilsTipoUsuario {

    public static TipoUsuario getTipoUsuario() {
        return new TipoUsuario(
                1L,
                "cliente"
        );
    }
}
