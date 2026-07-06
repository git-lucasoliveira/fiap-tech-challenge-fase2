package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.infrastructure.persistence.entity.TipoUsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTipoUsuarioRepository extends JpaRepository<TipoUsuarioJpaEntity, Long> {
}

