package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.infrastructure.persistence.entity.ItemCardapioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataItemCardapioRepository extends JpaRepository<ItemCardapioJpaEntity, Long> {
    List<ItemCardapioJpaEntity> findByRestauranteId(Long restauranteId);
}

