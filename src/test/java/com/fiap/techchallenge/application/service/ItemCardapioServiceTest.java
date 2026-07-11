package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.ItemCardapioView;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.ItemCardapio;
import com.fiap.techchallenge.domain.repository.ItemCardapioRepository;
import com.fiap.techchallenge.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsItemCardapioTest.*;
import static com.fiap.techchallenge.utils.UtilsRestauranteTest.getRestaurante;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ItemCardapioServiceTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private ItemCardapioService itemCardapioService;


    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void criarTest() {
        when(restauranteRepository.findById(any(Long.class))).thenReturn(Optional.of(getRestaurante()));
        when(itemCardapioRepository.save(any())).thenReturn(getItemCardapio());

        ItemCardapioView itemResponse = itemCardapioService.criar(getCreateItemCardapioCommand());

        assertThat(itemResponse)
                .isNotNull()
                .isInstanceOf(ItemCardapioView.class)
        ;

        assertThat(itemResponse.id())
                .isNotNull();
    }

    @Test
    void criarIdRestauranteNotFountTest(){
        when(restauranteRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> itemCardapioService.criar(getCreateItemCardapioCommand()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Restaurante com id 1 não encontrado.");

        verify(restauranteRepository, times(1)).findById(1L);
    }

    @Test
    void criarWithIdRestauranteNullTest(){
        when(restauranteRepository.findById(null)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> itemCardapioService.criar(getCreateItemCardapioCommandWithRestauranteIdNull()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Restaurante com id null não encontrado.");

        verify(restauranteRepository, times(0)).findById(1L);
    }

    @Test
    void buscarPorIdTest(){
        when(itemCardapioRepository.findById(1L)).thenReturn(Optional.of(getItemCardapio()));

        ItemCardapioView itemResponse = itemCardapioService.buscarPorId(1L);

        assertThat(itemResponse)
                .isNotNull()
                .isInstanceOf(ItemCardapioView.class)
                .isEqualTo( getItemCardapioView())
        ;
    }

    @Test
    void buscarPorIdNotFoundTest(){
        when(itemCardapioRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> itemCardapioService.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ItemCardapio com id 1 não encontrado.");

        verify(itemCardapioRepository, times(1)).findById(1L);
    }

    @Test
    void listarTest(){
        when(itemCardapioRepository.findAll()).thenReturn(getItemCardapioList());

        List<ItemCardapioView> itemResponse = itemCardapioService.listar();

        assertThat(itemResponse)
                .isNotNull()
                .isInstanceOf(Collection.class)
                .isEqualTo(getItemCardapioViewList())
        ;
    }

    @Test
    void listarPorRestauranteTest(){
        List<ItemCardapio> filteredList = getItemCardapioList().stream()
                .filter(item -> item.restauranteId().equals(1L))
                .toList();

        List<ItemCardapioView> filteredListView = getItemCardapioViewList().stream()
                .filter(item -> item.restauranteId().equals(1L))
                .toList();

        when(itemCardapioRepository.findByRestauranteId(anyLong())).thenReturn(filteredList);

        List<ItemCardapioView> itemResponse = itemCardapioService.listarPorRestaurante(1L);

        assertThat(itemResponse)
                .isNotNull()
                .isInstanceOf(Collection.class)
                .isEqualTo(filteredListView)
        ;
    }

    @Test
    void atualizarTest(){
        when(itemCardapioRepository.findById(any())).thenReturn(Optional.of(getItemCardapio()));
        when(itemCardapioRepository.save(any(ItemCardapio.class))).thenReturn(getItemCardapioUpdated());

        ItemCardapioView itemCardapioresponse = itemCardapioService.atualizar(1L, getUpdateItemCardapioCommand());

        assertThat(itemCardapioresponse)
                .isNotNull()
                .isInstanceOf(ItemCardapioView.class)
                .isEqualTo(getItemCardapioViewUpdated());

    }

    @Test
    void atualizarIdNotFoundTest(){
        when(itemCardapioRepository.findById(any())).thenReturn(Optional.empty());


        assertThatThrownBy(
                () -> itemCardapioService.atualizar(1L, getUpdateItemCardapioCommand()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ItemCardapio com id 1 não encontrado.");

        verify(itemCardapioRepository, times(1)).findById(1L);
    }

    @Test
    void deletarTest(){
        when(itemCardapioRepository.findById(any())).thenReturn(Optional.of(getItemCardapio()));
        doNothing().when(itemCardapioRepository).deleteById(any());

        itemCardapioService.deletar(1L);

        verify(itemCardapioRepository, times(1)).deleteById(any());
    }
}
