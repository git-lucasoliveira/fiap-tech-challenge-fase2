package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.EnderecoCommand;
import com.fiap.techchallenge.application.service.EnderecoService;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.presentation.dto.EnderecoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fiap.techchallenge.utils.UtilsEnderecoTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    @Test
    void criarTest() {
        when(enderecoService.criar(any(EnderecoCommand.class))).thenReturn(getEnderecoView());

        EnderecoResponse enderecoResponse = enderecoController.criar(getEnderecoRequest());

        assertThat(enderecoResponse)
                .isNotNull()
                .isEqualTo(getEnderecoResponse());
    }

    @Test
    void listarTest() {
        when(enderecoService.listar()).thenReturn(getEnderecoViewList());

        List<EnderecoResponse> enderecoResponsesList = enderecoController.listar();

        assertThat(enderecoResponsesList)
                .isNotNull()
                .isEqualTo(getEnderecoResponseList())
                .isInstanceOf(List.class);
    }

    @Test
    void buscarPorIdTest() {
        when(enderecoService.buscarPorId(1L)).thenReturn(getEnderecoView());

        EnderecoResponse enderecoResponse = enderecoController.buscarPorId(1L);

        assertThat(enderecoResponse)
                .isNotNull()
                .isEqualTo(getEnderecoResponse());
    }

    @Test
    void buscarPorIdNotFoundTest() {
        when(enderecoService.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("Endereco", 1L));

        assertThatThrownBy(
                () -> enderecoController.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Endereco com id 1 não encontrado.");

        verify(enderecoService, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizarTest(){
        when(enderecoService.atualizar(anyLong(), any())).thenReturn(getEnderecoView());

        EnderecoResponse enderecoResponse = enderecoController.atualizar(1L, getEnderecoRequest());

        assertThat(enderecoResponse)
                .isNotNull()
                .isEqualTo(getEnderecoResponse());
    }

    @Test
    void atualizarNotFoundTest(){
        when(enderecoService.atualizar(anyLong(), any())).thenThrow(new ResourceNotFoundException("Endereco", 1L));

        assertThatThrownBy(
                () -> enderecoController.atualizar(1L, getEnderecoRequestUpdate()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Endereco com id 1 não encontrado.");

        verify(enderecoService, times(1)).atualizar(1L, getUpdateEnderecoCommand());
    }

    @Test
    void deletarTest(){
        doNothing().when(enderecoService).deletar(1L);

        enderecoController.deletar(1L);

        verify(enderecoService, times(1)).deletar(1L);
    }
}
