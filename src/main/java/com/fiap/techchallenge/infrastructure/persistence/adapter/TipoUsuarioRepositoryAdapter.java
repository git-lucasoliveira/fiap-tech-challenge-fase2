package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.TipoUsuario;
import com.fiap.techchallenge.domain.repository.TipoUsuarioRepository;
import com.fiap.techchallenge.infrastructure.persistence.entity.TipoUsuarioJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataTipoUsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TipoUsuarioRepositoryAdapter implements TipoUsuarioRepository {

    private final SpringDataTipoUsuarioRepository springRepo;

    public TipoUsuarioRepositoryAdapter(SpringDataTipoUsuarioRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public TipoUsuario save(TipoUsuario tipoUsuario) {
        return toDomain(springRepo.save(toEntity(tipoUsuario)));
    }

    @Override
    public Optional<TipoUsuario> findById(Long id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<TipoUsuario> findAll() {
        return springRepo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepo.deleteById(id);
    }

    private TipoUsuarioJpaEntity toEntity(TipoUsuario t) {
        TipoUsuarioJpaEntity entity = new TipoUsuarioJpaEntity();
        entity.setId(t.id());
        entity.setNome(t.nome());
        return entity;
    }

    private TipoUsuario toDomain(TipoUsuarioJpaEntity e) {
        return new TipoUsuario(e.getId(), e.getNome());
    }
}

