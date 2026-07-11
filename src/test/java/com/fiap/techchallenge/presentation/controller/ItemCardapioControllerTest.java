package com.fiap.techchallenge.presentation.controller;

import com.fiap.techchallenge.application.dto.CreateItemCardapioCommand;
import com.fiap.techchallenge.application.service.ItemCardapioService;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.presentation.dto.ItemCardapioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fiap.techchallenge.utils.UtilsItemCardapioTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemCardapioControllerTest {

    @Mock
    private ItemCardapioService itemCardapioService;

    @InjectMocks
    private ItemCardapioController itemCardapioController;

    @Test
    void criarTest() {
        when(itemCardapioService.criar(any(CreateItemCardapioCommand.class))).thenReturn(getItemCardapioView());

        ItemCardapioResponse itemCardapioResponse = itemCardapioController.criar(getCreateItemCardapioRequest());

        assertThat(itemCardapioResponse)
                .isNotNull()
                .isEqualTo(getItemCardapioResponse());
    }

    @Test
    void listarPorRestaurante() {
        when(itemCardapioService.listarPorRestaurante(1L)).thenReturn(getItemCardapioViewList());

        List<ItemCardapioResponse> itemCardapioResponses = itemCardapioController.listar(1L);

        assertThat(itemCardapioResponses)
                .isNotNull()
                .isEqualTo(getItemCardapioResponseList())
                .isInstanceOf(List.class);
    }

    @Test
    void ListarTest(){
        when(itemCardapioService.listar()).thenReturn(getItemCardapioViewList());

        List<ItemCardapioResponse> itemCardapioResponses = itemCardapioController.listar(null);

        assertThat(itemCardapioResponses)
                .isNotNull()
                .isEqualTo(getItemCardapioResponseList())
                .isInstanceOf(List.class);
    }

    @Test
    void buscarPorIdTest() {
        when(itemCardapioService.buscarPorId(1L)).thenReturn(getItemCardapioView());

        ItemCardapioResponse itemCardapioResponse = itemCardapioController.buscarPorId(1L);

        assertThat(itemCardapioResponse)
                .isNotNull()
                .isEqualTo(getItemCardapioResponse());
    }

    @Test
    void buscarPorIdNotFoundTest() {
        when(itemCardapioService.buscarPorId(1L)).thenThrow(new ResourceNotFoundException("ItemCardapio", 1L));

        assertThatThrownBy(
                () -> itemCardapioController.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ItemCardapio com id 1 não encontrado.");

        verify(itemCardapioService, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizarTest(){
        when(itemCardapioService.atualizar(anyLong(), any())).thenReturn(getItemCardapioView());

        ItemCardapioResponse itemCardapioResponse = itemCardapioController.atualizar(1L, getUpdateItemCardapioRequest());

        assertThat(itemCardapioResponse)
                .isNotNull()
                .isEqualTo(getItemCardapioResponse());
    }

    @Test
    void atualizarNotFoundTest(){
        when(itemCardapioService.atualizar(anyLong(), any())).thenThrow(new ResourceNotFoundException("ItemCardapio", 1L));

        assertThatThrownBy(
                () -> itemCardapioController.atualizar(1L, getUpdateItemCardapioRequest()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ItemCardapio com id 1 não encontrado.");

        verify(itemCardapioService, times(1)).atualizar(1L, getUpdateItemCardapioCommand());
    }

    @Test
    void deletarTest(){
        doNothing().when(itemCardapioService).deletar(1L);

        itemCardapioController.deletar(1L);

        verify(itemCardapioService, times(1)).deletar(1L);
    }
}
