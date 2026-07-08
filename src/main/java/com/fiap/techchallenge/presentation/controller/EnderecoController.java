package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.EnderecoCommand;
import com.fiap.techchallenge.application.dto.EnderecoView;
import com.fiap.techchallenge.application.service.EnderecoService;
import com.fiap.techchallenge.presentation.dto.EnderecoRequest;
import com.fiap.techchallenge.presentation.dto.EnderecoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Endereços",
        description = "Endpoints para cadastro, consulta, atualização e exclusão de endereços"
)
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar endereço",
            description = "Cria um novo endereço com rua, número, cidade, CEP, complemento, estado e bairro."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    public EnderecoResponse criar(@RequestBody EnderecoRequest request) {
        return toResponse(enderecoService.criar(toCommand(request)));
    }

    @GetMapping
    @Operation(
            summary = "Listar endereços",
            description = "Retorna todos os endereços cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    })
    public List<EnderecoResponse> listar() {
        return enderecoService.listar()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar endereço por ID",
            description = "Retorna os dados de um endereço específico pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public EnderecoResponse buscarPorId(@PathVariable Long id) {
        return toResponse(enderecoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar endereço",
            description = "Atualiza os dados de um endereço existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public EnderecoResponse atualizar(@PathVariable Long id, @RequestBody EnderecoRequest request) {
        return toResponse(enderecoService.atualizar(id, toCommand(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir endereço",
            description = "Remove um endereço existente pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public void deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
    }

    private EnderecoCommand toCommand(EnderecoRequest r) {
        return new EnderecoCommand(
                r.rua(),
                r.numero(),
                r.cidade(),
                r.cep(),
                r.complemento(),
                r.estado(),
                r.bairro()
        );
    }

    private EnderecoResponse toResponse(EnderecoView v) {
        return new EnderecoResponse(
                v.id(),
                v.rua(),
                v.numero(),
                v.cidade(),
                v.cep(),
                v.complemento(),
                v.estado(),
                v.bairro()
        );
    }
}