package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.CreateItemCardapioCommand;
import com.fiap.techchallenge.application.dto.ItemCardapioView;
import com.fiap.techchallenge.application.dto.UpdateItemCardapioCommand;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.ItemCardapio;
import com.fiap.techchallenge.domain.model.Restaurante;
import com.fiap.techchallenge.domain.repository.ItemCardapioRepository;
import com.fiap.techchallenge.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemCardapioService {

    private final ItemCardapioRepository itemCardapioRepository;
    private final RestauranteRepository restauranteRepository;

    public ItemCardapioService(ItemCardapioRepository itemCardapioRepository, RestauranteRepository restauranteRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.restauranteRepository =  restauranteRepository;
    }

    @Transactional
    public ItemCardapioView criar(CreateItemCardapioCommand command) {
        validarRestauranteExistente(command.restauranteId());

        ItemCardapio item = new ItemCardapio(null,
                command.nome(),
                command.descricao(),
                command.preco(),
                command.disponivelLocal(),
                command.caminhoFoto(),
                command.restauranteId());
        return toView(itemCardapioRepository.save(item));
    }

    @Transactional
    public ItemCardapioView buscarPorId(Long id) {
        return itemCardapioRepository.findById(id)
                .map(this::toView)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCardapio", id));
    }

    @Transactional
    public List<ItemCardapioView> listar() {

        return itemCardapioRepository.findAll().stream().map(this::toView).toList();
    }

    @Transactional
    public List<ItemCardapioView> listarPorRestaurante(Long restauranteId) {

        return itemCardapioRepository.findByRestauranteId(restauranteId).stream().map(this::toView).toList();
    }

    @Transactional
    public ItemCardapioView atualizar(Long id, UpdateItemCardapioCommand command) {
        ItemCardapio existing = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCardapio", id));

        ItemCardapio atualizado = new ItemCardapio(
                id,
                command.nome(),
                command.descricao(),
                command.preco(),
                command.disponivelLocal(),
                command.caminhoFoto(),
                existing.restauranteId());
        return toView(itemCardapioRepository.save(atualizado));
    }

    @Transactional
    public void deletar(Long id) {
        itemCardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCardapio", id));
        itemCardapioRepository.deleteById(id);
    }

    private void validarRestauranteExistente(Long restauranteId) {
        if (restauranteId == null) {
            throw new ResourceNotFoundException("Restaurante", null);
        }

        restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", restauranteId));
    }

    private ItemCardapioView toView(ItemCardapio i) {
        return new ItemCardapioView(
                i.id(),
                i.nome(),
                i.descricao(),
                i.preco(),
                i.disponivelLocal(),
                i.caminhoFoto(),
                i.restauranteId());
    }
}

