package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.infrastructure.persistence.entity.EnderecoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataEnderecoRepository extends JpaRepository<EnderecoJpaEntity, Long> {
}

