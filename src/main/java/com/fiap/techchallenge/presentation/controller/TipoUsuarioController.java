package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.TipoUsuarioCommand;
import com.fiap.techchallenge.application.dto.TipoUsuarioView;
import com.fiap.techchallenge.application.service.TipoUsuarioService;
import com.fiap.techchallenge.presentation.dto.TipoUsuarioRequest;
import com.fiap.techchallenge.presentation.dto.TipoUsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-usuario")
public class TipoUsuarioController {

    private final TipoUsuarioService tipoUsuarioService;

    public TipoUsuarioController(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoUsuarioResponse criar(@Valid @RequestBody TipoUsuarioRequest request) {
        return toResponse(tipoUsuarioService.criar(new TipoUsuarioCommand(request.nome())));
    }

    @GetMapping
    public List<TipoUsuarioResponse> listar() {
        return tipoUsuarioService.listar().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public TipoUsuarioResponse buscarPorId(@PathVariable Long id) {
        return toResponse(tipoUsuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public TipoUsuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioRequest request) {
        return toResponse(tipoUsuarioService.atualizar(id, new TipoUsuarioCommand(request.nome())));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        tipoUsuarioService.deletar(id);
    }

    private TipoUsuarioResponse toResponse(TipoUsuarioView v) {
        return new TipoUsuarioResponse(v.id(), v.nome());
    }
}

