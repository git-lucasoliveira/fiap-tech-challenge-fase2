package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.TipoUsuarioCommand;
import com.fiap.techchallenge.application.service.TipoUsuarioService;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.presentation.dto.TipoUsuarioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fiap.techchallenge.utils.UtilsTipoUsuarioTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoUsuarioControllerTest {
    @Mock
    private TipoUsuarioService tipoUsuarioService;

    @InjectMocks
    private TipoUsuarioController tipoUsuarioController;

    @Test
    void criarTest() {
        when(tipoUsuarioService.criar(any(TipoUsuarioCommand.class))).thenReturn(getTipoUsuarioView());

        TipoUsuarioResponse tipoUsuarioResponse = tipoUsuarioController.criar(getTipoUsuarioRequest());

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isEqualTo(getTipoUsuarioResponse());
    }

    @Test
    void listarTest() {
        when(tipoUsuarioService.listar()).thenReturn(getTipoUsuarioViewList());

        List<TipoUsuarioResponse> tipoUsuarioResponses = tipoUsuarioController.listar();

        assertThat(tipoUsuarioResponses)
                .isNotNull()
                .isEqualTo(getTipoUsuarioResponseList())
                .isInstanceOf(List.class);
    }

    @Test
    void buscarPorIdTest() {
        when(tipoUsuarioService.buscarPorId(1L)).thenReturn(getTipoUsuarioView());

        TipoUsuarioResponse tipoUsuarioResponse = tipoUsuarioController.buscarPorId(1L);

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isEqualTo(getTipoUsuarioResponse());
    }

    @Test
    void buscarPorIdNotFoundTest() {
        when(tipoUsuarioService.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("TipoUsuario", 1L));

        assertThatThrownBy(
                () -> tipoUsuarioController.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("TipoUsuario com id 1 não encontrado.");

        verify(tipoUsuarioService, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizarTest(){
        when(tipoUsuarioService.atualizar(anyLong(), any())).thenReturn(getTipoUsuarioViewUpdated());

        TipoUsuarioResponse tipoUsuarioResponse = tipoUsuarioController.atualizar(1L, getTipoUsuarioRequestUpdate());

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isEqualTo(getTipoUsuarioResponseUpdate());
    }

    @Test
    void atualizarNotFoundTest(){
        when(tipoUsuarioService.atualizar(anyLong(), any())).thenThrow(new ResourceNotFoundException("TipoUsuario", 1L));

        assertThatThrownBy(
                () -> tipoUsuarioController.atualizar(1L, getTipoUsuarioRequestUpdate()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("TipoUsuario com id 1 não encontrado.");

        verify(tipoUsuarioService, times(1)).atualizar(1L, getUpdateTipoUsuarioCommand());
    }

    @Test
    void deletarTest(){
        doNothing().when(tipoUsuarioService).deletar(1L);

        tipoUsuarioController.deletar(1L);

        verify(tipoUsuarioService, times(1)).deletar(1L);
    }
}