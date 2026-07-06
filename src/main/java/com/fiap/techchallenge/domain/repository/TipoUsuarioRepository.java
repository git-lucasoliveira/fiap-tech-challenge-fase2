package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository {
    TipoUsuario save(TipoUsuario tipoUsuario);
    Optional<TipoUsuario> findById(Long id);
    List<TipoUsuario> findAll();
    void deleteById(Long id);
}

