package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateItemCardapioCommand;
import com.fiap.techchallenge.application.dto.ItemCardapioView;
import com.fiap.techchallenge.application.dto.UpdateItemCardapioCommand;
import com.fiap.techchallenge.application.service.ItemCardapioService;
import com.fiap.techchallenge.presentation.dto.CreateItemCardapioRequest;
import com.fiap.techchallenge.presentation.dto.ItemCardapioResponse;
import com.fiap.techchallenge.presentation.dto.UpdateItemCardapioRequest;
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
@RequestMapping("/api/itens-cardapio")
@Tag(
        name = "Itens do Cardápio",
        description = "Endpoints para cadastro, consulta, atualização e exclusão dos itens vendidos pelos restaurantes"
)
public class ItemCardapioController {

    private final ItemCardapioService itemCardapioService;

    public ItemCardapioController(ItemCardapioService itemCardapioService) {
        this.itemCardapioService = itemCardapioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar item do cardápio",
            description = "Cria um novo item de cardápio vinculado a um restaurante, informando nome, descrição, preço, disponibilidade para consumo no local e caminho da foto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item do cardápio cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ItemCardapioResponse criar(@Valid @RequestBody CreateItemCardapioRequest request) {
        CreateItemCardapioCommand command = new CreateItemCardapioCommand(
                request.nome(),
                request.descricao(),
                request.preco(),
                request.disponivelLocal(),
                request.caminhoFoto(),
                request.restauranteId()
        );

        return toResponse(itemCardapioService.criar(command));
    }

    @GetMapping
    @Operation(
            summary = "Listar itens do cardápio",
            description = "Retorna todos os itens do cardápio cadastrados. Também permite filtrar os itens por restaurante usando o parâmetro restauranteId."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de itens do cardápio retornada com sucesso")
    })
    public List<ItemCardapioResponse> listar(
            @Parameter(description = "ID do restaurante para filtrar os itens do cardápio")
            @RequestParam(required = false) Long restauranteId
    ) {
        if (restauranteId != null) {
            return itemCardapioService.listarPorRestaurante(restauranteId)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return itemCardapioService.listar()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar item do cardápio por ID",
            description = "Retorna os dados de um item específico do cardápio pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item do cardápio encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do cardápio não encontrado")
    })
    public ItemCardapioResponse buscarPorId(@PathVariable Long id) {
        return toResponse(itemCardapioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar item do cardápio",
            description = "Atualiza os dados de um item do cardápio existente, como nome, descrição, preço, disponibilidade e caminho da foto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item do cardápio atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Item do cardápio não encontrado")
    })
    public ItemCardapioResponse atualizar(@PathVariable Long id, @Valid @RequestBody UpdateItemCardapioRequest request) {
        UpdateItemCardapioCommand command = new UpdateItemCardapioCommand(
                request.nome(),
                request.descricao(),
                request.preco(),
                request.disponivelLocal(),
                request.caminhoFoto()
        );

        return toResponse(itemCardapioService.atualizar(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir item do cardápio",
            description = "Remove um item do cardápio existente pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item do cardápio excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do cardápio não encontrado")
    })
    public void deletar(@PathVariable Long id) {
        itemCardapioService.deletar(id);
    }

    private ItemCardapioResponse toResponse(ItemCardapioView v) {
        return new ItemCardapioResponse(
                v.id(),
                v.nome(),
                v.descricao(),
                v.preco(),
                v.disponivelLocal(),
                v.caminhoFoto(),
                v.restauranteId()
        );
    }
}