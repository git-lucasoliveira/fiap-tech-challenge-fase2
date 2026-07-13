package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.application.dto.CreateItemCardapioCommand;
import com.fiap.techchallenge.application.dto.ItemCardapioView;
import com.fiap.techchallenge.application.dto.UpdateItemCardapioCommand;
import com.fiap.techchallenge.domain.model.ItemCardapio;
import com.fiap.techchallenge.infrastructure.persistence.entity.ItemCardapioJpaEntity;
import com.fiap.techchallenge.presentation.dto.CreateItemCardapioRequest;
import com.fiap.techchallenge.presentation.dto.ItemCardapioResponse;
import com.fiap.techchallenge.presentation.dto.UpdateItemCardapioRequest;

import java.math.BigDecimal;
import java.util.List;

public abstract class UtilsItemCardapioTest {

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

    public static ItemCardapio getItemCardapioUpdated() {
        return new ItemCardapio(
                1L,
                "Item Teste Atualizado",
                "Descrição do item teste atualizado",
                BigDecimal.valueOf(29.99),
                false,
                "caminho/para/foto_atualizada.jpg",
                1L
        );
    }

    public static List<ItemCardapio> getItemCardapioList() {
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

    public static CreateItemCardapioCommand getCreateItemCardapioCommand() {
        return new CreateItemCardapioCommand(
                "Item Teste",
                "Descrição do item teste",
                BigDecimal.valueOf(19.99),
                true,
                "caminho/para/foto.jpg",
                1L
        );
    }

    public static CreateItemCardapioCommand getCreateItemCardapioCommandWithRestauranteIdNull() {
        return new CreateItemCardapioCommand(
                "Item Teste",
                "Descrição do item teste",
                BigDecimal.valueOf(19.99),
                true,
                "caminho/para/foto.jpg",
                null
        );
    }

    public static ItemCardapioView getItemCardapioView() {
        return new ItemCardapioView(
                1L,
                "Item Teste",
                "Descrição do item teste",
                BigDecimal.valueOf(19.99),
                true,
                "caminho/para/foto.jpg",
                1L
        );
    }

    public static ItemCardapioView getItemCardapioViewUpdated() {
        return new ItemCardapioView(
                1L,
                "Item Teste Atualizado",
                "Descrição do item teste atualizado",
                BigDecimal.valueOf(29.99),
                false,
                "caminho/para/foto_atualizada.jpg",
                1L
        );
    }

    public static List<ItemCardapioView> getItemCardapioViewList() {
        return List.of(
                getItemCardapioView(),
                new ItemCardapioView(
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

    public static UpdateItemCardapioCommand getUpdateItemCardapioCommand() {
        return new UpdateItemCardapioCommand(
                "Item Teste Atualizado",
                "Descrição do item teste atualizado",
                BigDecimal.valueOf(29.99),
                false,
                "caminho/para/foto_atualizada.jpg"
        );
    }

    public static ItemCardapioJpaEntity getItemCardapioJpaEntity() {
        ItemCardapioJpaEntity itemCardapioJpaEntity = new ItemCardapioJpaEntity();
        itemCardapioJpaEntity.setId(1L);
        itemCardapioJpaEntity.setNome("Item Teste");
        itemCardapioJpaEntity.setDescricao("Descrição do item teste");
        itemCardapioJpaEntity.setPreco(BigDecimal.valueOf(19.99));
        itemCardapioJpaEntity.setDisponivelLocal(true);
        itemCardapioJpaEntity.setCaminhoFoto("caminho/para/foto.jpg");
        itemCardapioJpaEntity.setRestauranteId(1L);
        return itemCardapioJpaEntity;
    }

    public static List<ItemCardapioJpaEntity> getItemCardapioJpaEntityList() {
        ItemCardapioJpaEntity itemCardapioJpaEntity = getItemCardapioJpaEntity();
        itemCardapioJpaEntity.setRestauranteId(2L);
        return List.of(
                getItemCardapioJpaEntity(),
                itemCardapioJpaEntity
        );
    }

    public static CreateItemCardapioRequest getCreateItemCardapioRequest() {
        return new CreateItemCardapioRequest(
                "Item Teste",
                "Descrição do item teste",
                BigDecimal.valueOf(19.99),
                true,
                "caminho/para/foto.jpg",
                1L
        );
    }

    public static ItemCardapioResponse getItemCardapioResponse() {
        return new ItemCardapioResponse(
                1L,
                "Item Teste",
                "Descrição do item teste",
                BigDecimal.valueOf(19.99),
                true,
                "caminho/para/foto.jpg",
                1L
        );
    }

    public static List<ItemCardapioResponse> getItemCardapioResponseList() {
        return List.of(
                getItemCardapioResponse(),
                new ItemCardapioResponse(
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

    public static UpdateItemCardapioRequest getUpdateItemCardapioRequest() {
        return new UpdateItemCardapioRequest(
                "Item Teste Atualizado",
                "Descrição do item teste atualizado",
                BigDecimal.valueOf(29.99),
                false,
                "caminho/para/foto_atualizada.jpg"
        );
    }
}