package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateUserCommand;
import com.fiap.techchallenge.application.dto.UpdateUserCommand;
import com.fiap.techchallenge.application.dto.UserView;
import com.fiap.techchallenge.application.service.UserService;
import com.fiap.techchallenge.presentation.dto.CreateUserRequest;
import com.fiap.techchallenge.presentation.dto.UpdateUserRequest;
import com.fiap.techchallenge.presentation.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Usuários",
        description = "Endpoints para cadastro, consulta, atualização e exclusão de usuários"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar usuário",
            description = "Cria um novo usuário e associa esse usuário a um tipo de usuário existente por meio do campo fkTipoUsuario."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário ou endereço não encontrado")
    })
    public UserResponse criar(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(
                request.nome(),
                request.email(),
                request.login(),
                request.senha(),
                request.fkTipoUsuario(),
                request.enderecoId()
        );

        return toResponse(userService.criar(command));
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    public List<UserResponse> listar() {
        return userService.listar()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UserResponse buscarPorId(@PathVariable Long id) {
        return toResponse(userService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente, incluindo a associação com um tipo de usuário por meio do campo fkTipoUsuario."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Usuário, tipo de usuário ou endereço não encontrado")
    })
    public UserResponse atualizar(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        UpdateUserCommand command = new UpdateUserCommand(
                request.nome(),
                request.email(),
                request.login(),
                request.senha(),
                request.fkTipoUsuario(),
                request.enderecoId()
        );

        return toResponse(userService.atualizar(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário existente pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public void deletar(@PathVariable Long id) {
        userService.deletar(id);
    }

    private UserResponse toResponse(UserView v) {
        return new UserResponse(
                v.id(),
                v.nome(),
                v.email(),
                v.login(),
                v.fkTipoUsuario(),
                v.enderecoId(),
                v.dataUltimaAlteracao()
        );
    }
}