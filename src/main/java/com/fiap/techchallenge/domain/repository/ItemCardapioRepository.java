package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.ItemCardapio;

import java.util.List;
import java.util.Optional;

public interface ItemCardapioRepository {
    ItemCardapio save(ItemCardapio itemCardapio);
    Optional<ItemCardapio> findById(Long id);
    List<ItemCardapio> findAll();
    List<ItemCardapio> findByRestauranteId(Long restauranteId);
    void deleteById(Long id);
}

