package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.Restaurante;
import com.fiap.techchallenge.domain.repository.RestauranteRepository;
import com.fiap.techchallenge.infrastructure.persistence.entity.RestauranteJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataRestauranteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteRepositoryAdapter implements RestauranteRepository {

    private final SpringDataRestauranteRepository springRepo;

    public RestauranteRepositoryAdapter(SpringDataRestauranteRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Restaurante save(Restaurante restaurante) {
        return toDomain(springRepo.save(toEntity(restaurante)));
    }

    @Override
    public Optional<Restaurante> findById(Long id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Restaurante> findAll() {
        return springRepo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Restaurante> findByDonoId(Long donoId) {
        return springRepo.findByDonoId(donoId).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepo.deleteById(id);
    }

    private RestauranteJpaEntity toEntity(Restaurante r) {
        RestauranteJpaEntity entity = new RestauranteJpaEntity();
        entity.setId(r.id());
        entity.setNome(r.nome());
        entity.setTipoCozinha(r.tipoCozinha());
        entity.setHorarioFuncionamento(r.horarioFuncionamento());
        entity.setDonoId(r.donoId());
        entity.setEnderecoId(r.enderecoId());
        return entity;
    }

    private Restaurante toDomain(RestauranteJpaEntity e) {
        return new Restaurante(e.getId(), e.getNome(), e.getTipoCozinha(),
                e.getHorarioFuncionamento(), e.getDonoId(), e.getEnderecoId());
    }
}

