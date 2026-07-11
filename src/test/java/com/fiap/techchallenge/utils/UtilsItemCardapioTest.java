package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.domain.model.ItemCardapio;

import java.math.BigDecimal;
import java.util.List;

public abstract class UtilsItemCardapio {

     public static ItemCardapio getItemCardapio() {
        return new ItemCardapio(
                1L,
                "Item Teste",
                "Descrição do item teste",
                BigDecimal.valueOf(19.99),
                true,
                "caminho/para/foto.jpg",
                1L
        );
    }

    public static List<ItemCardapio> getListItemCardapio() {
        return List.of(
                new ItemCardapio(
                        1L,
                        "Item Teste",
                        "Descrição do item teste",
                        BigDecimal.valueOf(19.99),
                        true,
                        "caminho/para/foto.jpg",
                        1L
                ),
                new ItemCardapio(
                        1L,
                        "Item Teste",
                        "Descrição do item teste",
                        BigDecimal.valueOf(19.99),
                        true,
                        "caminho/para/foto.jpg",
                        2L
                )
        );
    }
}
