package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateItemCardapioCommand;
import com.fiap.techchallenge.application.dto.ItemCardapioView;
import com.fiap.techchallenge.application.dto.UpdateItemCardapioCommand;
import com.fiap.techchallenge.application.service.ItemCardapioService;
import com.fiap.techchallenge.presentation.dto.CreateItemCardapioRequest;
import com.fiap.techchallenge.presentation.dto.ItemCardapioResponse;
import com.fiap.techchallenge.presentation.dto.UpdateItemCardapioRequest;
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
@RequestMapping("/api/itens-cardapio")
public class ItemCardapioController {

    private final ItemCardapioService itemCardapioService;

    public ItemCardapioController(ItemCardapioService itemCardapioService) {
        this.itemCardapioService = itemCardapioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemCardapioResponse criar(@Valid @RequestBody CreateItemCardapioRequest request) {
        CreateItemCardapioCommand command = new CreateItemCardapioCommand(request.nome(), request.descricao(),
                request.preco(), request.disponivelLocal(), request.caminhoFoto(), request.restauranteId());
        return toResponse(itemCardapioService.criar(command));
    }

    @GetMapping
    public List<ItemCardapioResponse> listar(@RequestParam(required = false) Long restauranteId) {
        if (restauranteId != null) {
            return itemCardapioService.listarPorRestaurante(restauranteId).stream().map(this::toResponse).toList();
        }
        return itemCardapioService.listar().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ItemCardapioResponse buscarPorId(@PathVariable Long id) {
        return toResponse(itemCardapioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ItemCardapioResponse atualizar(@PathVariable Long id, @RequestBody UpdateItemCardapioRequest request) {
        UpdateItemCardapioCommand command = new UpdateItemCardapioCommand(request.nome(), request.descricao(),
                request.preco(), request.disponivelLocal(), request.caminhoFoto());
        return toResponse(itemCardapioService.atualizar(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        itemCardapioService.deletar(id);
    }

    private ItemCardapioResponse toResponse(ItemCardapioView v) {
        return new ItemCardapioResponse(v.id(), v.nome(), v.descricao(),
                v.preco(), v.disponivelLocal(), v.caminhoFoto(), v.restauranteId());
    }
}

