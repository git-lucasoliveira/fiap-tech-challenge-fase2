package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.infrastructure.persistence.entity.RestauranteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataRestauranteRepository extends JpaRepository<RestauranteJpaEntity, Long> {
    List<RestauranteJpaEntity> findByDonoId(Long donoId);
}

