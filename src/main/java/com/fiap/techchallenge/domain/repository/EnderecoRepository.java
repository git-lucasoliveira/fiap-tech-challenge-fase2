package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository {
    Endereco save(Endereco endereco);
    Optional<Endereco> findById(Long id);
    List<Endereco> findAll();
    void deleteById(Long id);
}

