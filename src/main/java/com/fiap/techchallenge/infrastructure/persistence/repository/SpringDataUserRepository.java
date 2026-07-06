package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {
}

