package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.Endereco;
import com.fiap.techchallenge.domain.repository.EnderecoRepository;
import com.fiap.techchallenge.infrastructure.persistence.entity.EnderecoJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataEnderecoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnderecoRepositoryAdapter implements EnderecoRepository {

    private final SpringDataEnderecoRepository springRepo;

    public EnderecoRepositoryAdapter(SpringDataEnderecoRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Endereco save(Endereco endereco) {
        return toDomain(springRepo.save(toEntity(endereco)));
    }

    @Override
    public Optional<Endereco> findById(Long id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Endereco> findAll() {
        return springRepo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepo.deleteById(id);
    }

    private EnderecoJpaEntity toEntity(Endereco e) {
        EnderecoJpaEntity entity = new EnderecoJpaEntity();
        entity.setId(e.id());
        entity.setRua(e.rua());
        entity.setNumero(e.numero());
        entity.setCidade(e.cidade());
        entity.setCep(e.cep());
        entity.setComplemento(e.complemento());
        entity.setEstado(e.estado());
        entity.setBairro(e.bairro());
        return entity;
    }

    private Endereco toDomain(EnderecoJpaEntity e) {
        return new Endereco(e.getId(), e.getRua(), e.getNumero(), e.getCidade(),
                e.getCep(), e.getComplemento(), e.getEstado(), e.getBairro());
    }
}

