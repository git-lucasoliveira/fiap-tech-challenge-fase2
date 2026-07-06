package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateRestauranteCommand;
import com.fiap.techchallenge.application.dto.RestauranteView;
import com.fiap.techchallenge.application.dto.UpdateRestauranteCommand;
import com.fiap.techchallenge.application.service.RestauranteService;
import com.fiap.techchallenge.presentation.dto.CreateRestauranteRequest;
import com.fiap.techchallenge.presentation.dto.RestauranteResponse;
import com.fiap.techchallenge.presentation.dto.UpdateRestauranteRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponse criar(@Valid @RequestBody CreateRestauranteRequest request) {
        CreateRestauranteCommand command = new CreateRestauranteCommand(request.nome(), request.tipoCozinha(),
                request.horarioFuncionamento(), request.donoId(), request.enderecoId());
        return toResponse(restauranteService.criar(command));
    }

    @GetMapping
    public List<RestauranteResponse> listar(@RequestParam(required = false) Long donoId) {
        if (donoId != null) {
            return restauranteService.listarPorDono(donoId).stream().map(this::toResponse).toList();
        }
        return restauranteService.listar().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public RestauranteResponse buscarPorId(@PathVariable Long id) {
        return toResponse(restauranteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public RestauranteResponse atualizar(@PathVariable Long id, @Valid @RequestBody UpdateRestauranteRequest request) {
        UpdateRestauranteCommand command = new UpdateRestauranteCommand(request.nome(), request.tipoCozinha(),
                request.horarioFuncionamento(), request.donoId(), request.enderecoId());
        return toResponse(restauranteService.atualizar(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        restauranteService.deletar(id);
    }

    private RestauranteResponse toResponse(RestauranteView v) {
        return new RestauranteResponse(v.id(), v.nome(), v.tipoCozinha(),
                v.horarioFuncionamento(), v.donoId(), v.enderecoId());
    }
}

