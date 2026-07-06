package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository {
    Restaurante save(Restaurante restaurante);
    Optional<Restaurante> findById(Long id);
    List<Restaurante> findAll();
    List<Restaurante> findByDonoId(Long donoId);
    void deleteById(Long id);
}

