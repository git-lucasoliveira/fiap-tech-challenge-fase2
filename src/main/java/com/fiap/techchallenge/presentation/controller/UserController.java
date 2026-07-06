package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateUserCommand;
import com.fiap.techchallenge.application.dto.UpdateUserCommand;
import com.fiap.techchallenge.application.dto.UserView;
import com.fiap.techchallenge.application.service.UserService;
import com.fiap.techchallenge.presentation.dto.CreateUserRequest;
import com.fiap.techchallenge.presentation.dto.UpdateUserRequest;
import com.fiap.techchallenge.presentation.dto.UserResponse;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse criar(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(request.nome(), request.email(),
                request.login(), request.senha(), request.fkTipoUsuario(), request.enderecoId());
        return toResponse(userService.criar(command));
    }

    @GetMapping
    public List<UserResponse> listar() {
        return userService.listar().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UserResponse buscarPorId(@PathVariable Long id) {
        return toResponse(userService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public UserResponse atualizar(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        UpdateUserCommand command = new UpdateUserCommand(request.nome(), request.email(),
                request.login(), request.senha(), request.fkTipoUsuario(), request.enderecoId());
        return toResponse(userService.atualizar(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        userService.deletar(id);
    }

    private UserResponse toResponse(UserView v) {
        return new UserResponse(v.id(), v.nome(), v.email(), v.login(),
                v.fkTipoUsuario(), v.enderecoId(), v.dataUltimaAlteracao());
    }
}

