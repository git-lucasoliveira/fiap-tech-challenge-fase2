package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.Endereco;
import com.fiap.techchallenge.infrastructure.persistence.entity.EnderecoJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataEnderecoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsEnderecoTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EnderecoRepositoryAdapterTest {
    @Mock
    private SpringDataEnderecoRepository springDataEnderecoRepository;

    @InjectMocks
    private EnderecoRepositoryAdapter enderecoRepositoryAdapter;

    @Test
    void saveTest() {
        Endereco endereco = getEndereco();

        when(springDataEnderecoRepository.save(any(EnderecoJpaEntity.class))).thenReturn(getEnderecoJpaEntity());

        Endereco enderecoResponse = enderecoRepositoryAdapter.save(endereco);

        assertEquals(endereco.id(), enderecoResponse.id());
        assertThat(enderecoResponse)
                .isNotNull()
                .isEqualTo(endereco);
    }

    @Test
    void findByIdTest() {
        when(springDataEnderecoRepository.findById(anyLong())).thenReturn(Optional.of(getEnderecoJpaEntity()));

        Optional<Endereco> enderecoResponse = enderecoRepositoryAdapter.findById(1L);

        assertThat(enderecoResponse)
                .isNotNull()
                .isEqualTo(Optional.of(getEndereco()));
    }

    @Test
    void findAll(){
        when(springDataEnderecoRepository.findAll()).thenReturn(getEnderecoJpaEntityList());

        List<Endereco> enderecoResponse = enderecoRepositoryAdapter.findAll();

        assertThat(enderecoResponse)
                .isNotNull()
                .isEqualTo(getEnderecoList())
                .isInstanceOf(Collection.class);
    }

    @Test
    void deleteById(){
        doNothing().when(springDataEnderecoRepository).deleteById(any());

        enderecoRepositoryAdapter.deleteById(1L);

        verify(springDataEnderecoRepository, times(1)).deleteById(any());
    }
}