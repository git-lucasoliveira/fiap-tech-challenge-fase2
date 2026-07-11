package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.ItemCardapio;
import com.fiap.techchallenge.infrastructure.persistence.entity.ItemCardapioJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataItemCardapioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsItemCardapioTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class ItemCardapioRepositoryAdapterTest {

    @Mock
    private SpringDataItemCardapioRepository springDataItemCardapioRepository;

    @InjectMocks
    private ItemCardapioRepositoryAdapter itemCardapioRepositoryAdapter;

    @Test
    void saveTest() {
        ItemCardapio itemCardapio = getItemCardapio();

        when(springDataItemCardapioRepository.save(any(ItemCardapioJpaEntity.class))).thenReturn(getItemCardapioJpaEntity());

        ItemCardapio itemCardapioResponse = itemCardapioRepositoryAdapter.save(itemCardapio);

        assertEquals(itemCardapio.id(), itemCardapioResponse.id());
        assertThat(itemCardapioResponse)
                .isNotNull()
                .isEqualTo(itemCardapio);
    }

    @Test
    void findByIdTest() {
        when(springDataItemCardapioRepository.findById(anyLong())).thenReturn(Optional.of(getItemCardapioJpaEntity()));

        Optional<ItemCardapio> itemCardapio = itemCardapioRepositoryAdapter.findById(1L);

        assertThat(itemCardapio)
                .isNotNull()
                .isEqualTo(Optional.of(getItemCardapio()));
    }

    @Test
    void findAll(){
        when(springDataItemCardapioRepository.findAll()).thenReturn(getItemCardapioJpaEntityList());

        List<ItemCardapio> itemCardapioList = itemCardapioRepositoryAdapter.findAll();

        assertThat(itemCardapioList)
                .isNotNull()
                .isEqualTo(getItemCardapioList())
                .isInstanceOf(Collection.class);
    }

    @Test
    void deleteById(){
        doNothing().when(springDataItemCardapioRepository).deleteById(any());

        itemCardapioRepositoryAdapter.deleteById(1L);

        verify(springDataItemCardapioRepository, times(1)).deleteById(any());
    }
}