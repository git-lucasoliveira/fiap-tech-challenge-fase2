package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.CreateRestauranteCommand;
import com.fiap.techchallenge.application.dto.RestauranteView;
import com.fiap.techchallenge.application.dto.UpdateRestauranteCommand;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.Restaurante;
import com.fiap.techchallenge.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional
    public RestauranteView criar(CreateRestauranteCommand command) {
        Restaurante restaurante = new Restaurante(null, command.nome(), command.tipoCozinha(),
                command.horarioFuncionamento(), command.donoId(), command.enderecoId());
        return toView(restauranteRepository.save(restaurante));
    }

    @Transactional
    public RestauranteView buscarPorId(Long id) {
        return restauranteRepository.findById(id)
                .map(this::toView)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", id));
    }

    @Transactional
    public List<RestauranteView> listar() {
        return restauranteRepository.findAll().stream().map(this::toView).toList();
    }

    @Transactional
    public List<RestauranteView> listarPorDono(Long donoId) {
        return restauranteRepository.findByDonoId(donoId).stream().map(this::toView).toList();
    }

    @Transactional
    public RestauranteView atualizar(Long id, UpdateRestauranteCommand command) {
        restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", id));
        Restaurante atualizado = new Restaurante(id, command.nome(), command.tipoCozinha(),
                command.horarioFuncionamento(), command.donoId(), command.enderecoId());
        return toView(restauranteRepository.save(atualizado));
    }

    @Transactional
    public void deletar(Long id) {
        restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", id));
        restauranteRepository.deleteById(id);
    }

    private RestauranteView toView(Restaurante r) {
        return new RestauranteView(r.id(), r.nome(), r.tipoCozinha(),
                r.horarioFuncionamento(), r.donoId(), r.enderecoId());
    }
}

