package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.TipoUsuarioCommand;
import com.fiap.techchallenge.application.dto.TipoUsuarioView;
import com.fiap.techchallenge.application.service.TipoUsuarioService;
import com.fiap.techchallenge.presentation.dto.TipoUsuarioRequest;
import com.fiap.techchallenge.presentation.dto.TipoUsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(
        name = "Tipos de Usuário",
        description = "Endpoints para cadastro, consulta, atualização e exclusão de tipos de usuário"
)
public class TipoUsuarioController {

    private final TipoUsuarioService tipoUsuarioService;

    public TipoUsuarioController(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar tipo de usuário",
            description = "Cria um novo tipo de usuário, como Cliente ou Dono de Restaurante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    public TipoUsuarioResponse criar(@Valid @RequestBody TipoUsuarioRequest request) {
        return toResponse(tipoUsuarioService.criar(new TipoUsuarioCommand(request.nome())));
    }

    @GetMapping
    @Operation(
            summary = "Listar tipos de usuário",
            description = "Retorna todos os tipos de usuário cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de usuário retornada com sucesso")
    })
    public List<TipoUsuarioResponse> listar() {
        return tipoUsuarioService.listar().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar tipo de usuário por ID",
            description = "Retorna os dados de um tipo de usuário específico pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public TipoUsuarioResponse buscarPorId(@PathVariable Long id) {
        return toResponse(tipoUsuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar tipo de usuário",
            description = "Atualiza o nome de um tipo de usuário existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public TipoUsuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioRequest request) {
        return toResponse(tipoUsuarioService.atualizar(id, new TipoUsuarioCommand(request.nome())));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir tipo de usuário",
            description = "Remove um tipo de usuário existente pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public void deletar(@PathVariable Long id) {
        tipoUsuarioService.deletar(id);
    }

    private TipoUsuarioResponse toResponse(TipoUsuarioView v) {
        return new TipoUsuarioResponse(v.id(), v.nome());
    }
}