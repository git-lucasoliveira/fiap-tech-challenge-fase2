package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.EnderecoCommand;
import com.fiap.techchallenge.application.dto.EnderecoView;
import com.fiap.techchallenge.application.service.EnderecoService;
import com.fiap.techchallenge.presentation.dto.EnderecoRequest;
import com.fiap.techchallenge.presentation.dto.EnderecoResponse;
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
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnderecoResponse criar(@RequestBody EnderecoRequest request) {
        return toResponse(enderecoService.criar(toCommand(request)));
    }

    @GetMapping
    public List<EnderecoResponse> listar() {
        return enderecoService.listar().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public EnderecoResponse buscarPorId(@PathVariable Long id) {
        return toResponse(enderecoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public EnderecoResponse atualizar(@PathVariable Long id, @RequestBody EnderecoRequest request) {
        return toResponse(enderecoService.atualizar(id, toCommand(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
    }

    private EnderecoCommand toCommand(EnderecoRequest r) {
        return new EnderecoCommand(r.rua(), r.numero(), r.cidade(), r.cep(),
                r.complemento(), r.estado(), r.bairro());
    }

    private EnderecoResponse toResponse(EnderecoView v) {
        return new EnderecoResponse(v.id(), v.rua(), v.numero(), v.cidade(),
                v.cep(), v.complemento(), v.estado(), v.bairro());
    }
}

