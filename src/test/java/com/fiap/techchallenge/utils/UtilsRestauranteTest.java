package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.domain.model.Restaurante;

public abstract class UtilsRestauranteTest {

    public static Restaurante getRestaurante() {
        return new Restaurante(
                1L,
                "Restaurante Teste",
                "Descrição do restaurante teste",
                "10 as 20",
                1L,
                1L
        );

    }
}
