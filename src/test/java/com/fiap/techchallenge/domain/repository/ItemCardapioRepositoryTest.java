package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.ItemCardapio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsItemCardapioTest.getItemCardapio;
import static com.fiap.techchallenge.utils.UtilsItemCardapioTest.getItemCardapioList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ItemCardapioRepositoryTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Test
    void TestItemCardapioSave(){
        ItemCardapio itemCardapio = getItemCardapio();

        when(itemCardapioRepository.save(any(ItemCardapio.class))).thenReturn(itemCardapio);

        ItemCardapio itemCardapioResponse = itemCardapioRepository.save(itemCardapio);

        assertThat(itemCardapioResponse).isNotNull().isEqualTo(itemCardapio);

    }

    @Test
    void TestItemCardapioFindById(){
        ItemCardapio itemCardapio = getItemCardapio();

        when(itemCardapioRepository.findById(1L)).thenReturn(Optional.of(itemCardapio));

        java.util.Optional<ItemCardapio> itemCardapioResponse = itemCardapioRepository.findById(1L);

        assertThat(itemCardapioResponse).isNotNull().isPresent();
    }

    @Test
    void testFindItemCardapioByIdNotFound() {
        when(itemCardapioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ItemCardapio> itemCardapioResponse = itemCardapioRepository.findById(1L);

        Assertions.assertFalse(itemCardapioResponse.isPresent());
    }


    @Test
    void TestItemCardapioFindAll(){

        List<ItemCardapio> itemCardapioList = getItemCardapioList();

        when(itemCardapioRepository.findAll()).thenReturn(itemCardapioList);

        List<ItemCardapio> itemCardapioResponseList = itemCardapioRepository.findAll();

        assertThat(itemCardapioResponseList)
                .isNotNull()
                .isEqualTo(itemCardapioList);

    }

    @Test
    void TestItemCardapioFindByRestauranteId(){
        List<ItemCardapio> itemCardapioList = getItemCardapioList();
        List<ItemCardapio> filteredList = itemCardapioList.stream()
                .filter(item -> item.restauranteId().equals(1L))
                .toList();
        when(itemCardapioRepository.findByRestauranteId(1L)).thenReturn(filteredList);

        List<ItemCardapio> itemCardapioResponseList = itemCardapioRepository.findByRestauranteId(1L);

        assertThat(itemCardapioResponseList)
                .isNotNull()
                .isEqualTo(filteredList);

    }

    @Test
    void TestItemCardapioDelete(){
        ItemCardapio itemCardapio = getItemCardapio();

        doNothing().when(itemCardapioRepository).deleteById(any());

        itemCardapioRepository.deleteById(itemCardapio.id());

        verify(itemCardapioRepository, times(1)).deleteById(any());
    }
}
