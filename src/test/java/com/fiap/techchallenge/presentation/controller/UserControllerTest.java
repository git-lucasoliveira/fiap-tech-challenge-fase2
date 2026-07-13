package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateUserCommand;
import com.fiap.techchallenge.application.service.UserService;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.presentation.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fiap.techchallenge.utils.UtilsUserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void criarTest() {
        when(userService.criar(any(CreateUserCommand.class))).thenReturn(getUserView());

        UserResponse userResponse = userController.criar(getCreateUserRequest());

        assertThat(userResponse)
                .isNotNull()
                .isEqualTo(getUserResponse());
    }

    @Test
    void listarTest() {
        when(userService.listar()).thenReturn(getUserViewList());

        List<UserResponse> userResponses = userController.listar();

        assertThat(userResponses)
                .isNotNull()
                .isEqualTo(getUserResponseList())
                .isInstanceOf(List.class);
    }

    @Test
    void buscarPorIdTest() {
        when(userService.buscarPorId(1L)).thenReturn(getUserView());

        UserResponse userResponse = userController.buscarPorId(1L);

        assertThat(userResponse)
                .isNotNull()
                .isEqualTo(getUserResponse());
    }

    @Test
    void buscarPorIdNotFoundTest() {
        when(userService.buscarPorId(1L)).thenThrow(new  ResourceNotFoundException("Usuário", 1L));

        assertThatThrownBy(
                () -> userController.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário com id 1 não encontrado.");

        verify(userService, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizarTest(){
        when(userService.atualizar(anyLong(), any())).thenReturn(getUserViewUpdated());

        UserResponse userResponse = userController.atualizar(1L, getUpdateUserRequest());

        assertThat(userResponse)
                .isNotNull()
                .isEqualTo(getUserResponseUpdated());
    }

    @Test
    void atualizarNotFoundTest(){
        when(userService.atualizar(anyLong(), any())).thenThrow(new ResourceNotFoundException("Usuário", 1L));

        assertThatThrownBy(
                () -> userController.atualizar(1L, getUpdateUserRequest()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário com id 1 não encontrado.");

        verify(userService, times(1)).atualizar(1L, getUpdateUserCommand());
    }

    @Test
    void deletarTest(){
        doNothing().when(userService).deletar(1L);

        userController.deletar(1L);

        verify(userService, times(1)).deletar(1L);
    }
}