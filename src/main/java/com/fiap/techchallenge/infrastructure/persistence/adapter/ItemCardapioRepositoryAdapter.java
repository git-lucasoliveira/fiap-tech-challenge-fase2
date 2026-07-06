package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.ItemCardapio;
import com.fiap.techchallenge.domain.repository.ItemCardapioRepository;
import com.fiap.techchallenge.infrastructure.persistence.entity.ItemCardapioJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataItemCardapioRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemCardapioRepositoryAdapter implements ItemCardapioRepository {

    private final SpringDataItemCardapioRepository springRepo;

    public ItemCardapioRepositoryAdapter(SpringDataItemCardapioRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public ItemCardapio save(ItemCardapio item) {
        return toDomain(springRepo.save(toEntity(item)));
    }

    @Override
    public Optional<ItemCardapio> findById(Long id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<ItemCardapio> findAll() {
        return springRepo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<ItemCardapio> findByRestauranteId(Long restauranteId) {
        return springRepo.findByRestauranteId(restauranteId).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepo.deleteById(id);
    }

    private ItemCardapioJpaEntity toEntity(ItemCardapio i) {
        ItemCardapioJpaEntity entity = new ItemCardapioJpaEntity();
        entity.setId(i.id());
        entity.setNome(i.nome());
        entity.setDescricao(i.descricao());
        entity.setPreco(i.preco());
        entity.setDisponivelLocal(i.disponivelLocal());
        entity.setCaminhoFoto(i.caminhoFoto());
        entity.setRestauranteId(i.restauranteId());
        return entity;
    }

    private ItemCardapio toDomain(ItemCardapioJpaEntity e) {
        return new ItemCardapio(e.getId(), e.getNome(), e.getDescricao(),
                e.getPreco(), e.getDisponivelLocal(), e.getCaminhoFoto(), e.getRestauranteId());
    }
}

