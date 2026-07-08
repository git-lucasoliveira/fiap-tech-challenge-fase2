package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateRestauranteCommand;
import com.fiap.techchallenge.application.dto.RestauranteView;
import com.fiap.techchallenge.application.dto.UpdateRestauranteCommand;
import com.fiap.techchallenge.application.service.RestauranteService;
import com.fiap.techchallenge.presentation.dto.CreateRestauranteRequest;
import com.fiap.techchallenge.presentation.dto.RestauranteResponse;
import com.fiap.techchallenge.presentation.dto.UpdateRestauranteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Restaurantes",
        description = "Endpoints para cadastro, consulta, atualização e exclusão de restaurantes"
)
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar restaurante",
            description = "Cria um novo restaurante informando nome, tipo de cozinha, horário de funcionamento, dono e endereço."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Dono ou endereço não encontrado")
    })
    public RestauranteResponse criar(@Valid @RequestBody CreateRestauranteRequest request) {
        CreateRestauranteCommand command = new CreateRestauranteCommand(
                request.nome(),
                request.tipoCozinha(),
                request.horarioFuncionamento(),
                request.donoId(),
                request.enderecoId()
        );

        return toResponse(restauranteService.criar(command));
    }

    @GetMapping
    @Operation(
            summary = "Listar restaurantes",
            description = "Retorna todos os restaurantes cadastrados. Também permite filtrar os restaurantes por dono usando o parâmetro donoId."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso")
    })
    public List<RestauranteResponse> listar(
            @Parameter(description = "ID do dono do restaurante para filtrar a listagem")
            @RequestParam(required = false) Long donoId
    ) {
        if (donoId != null) {
            return restauranteService.listarPorDono(donoId)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return restauranteService.listar()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar restaurante por ID",
            description = "Retorna os dados de um restaurante específico pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public RestauranteResponse buscarPorId(@PathVariable Long id) {
        return toResponse(restauranteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar restaurante",
            description = "Atualiza os dados de um restaurante existente, incluindo nome, tipo de cozinha, horário de funcionamento, dono e endereço."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Restaurante, dono ou endereço não encontrado")
    })
    public RestauranteResponse atualizar(@PathVariable Long id, @Valid @RequestBody UpdateRestauranteRequest request) {
        UpdateRestauranteCommand command = new UpdateRestauranteCommand(
                request.nome(),
                request.tipoCozinha(),
                request.horarioFuncionamento(),
                request.donoId(),
                request.enderecoId()
        );

        return toResponse(restauranteService.atualizar(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir restaurante",
            description = "Remove um restaurante existente pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public void deletar(@PathVariable Long id) {
        restauranteService.deletar(id);
    }

    private RestauranteResponse toResponse(RestauranteView v) {
        return new RestauranteResponse(
                v.id(),
                v.nome(),
                v.tipoCozinha(),
                v.horarioFuncionamento(),
                v.donoId(),
                v.enderecoId()
        );
    }
}