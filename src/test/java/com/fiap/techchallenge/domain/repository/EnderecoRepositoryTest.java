package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.Endereco;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsEnderecoTest.getEndereco;
import static com.fiap.techchallenge.utils.UtilsEnderecoTest.getEnderecoList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EnderecoRepositoryTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    AutoCloseable mock;

    @Test
    void saveTest(){
        Endereco endereco = getEndereco();

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        Endereco enderecoResponse = enderecoRepository.save(endereco);

        assertThat(enderecoResponse).isNotNull().isEqualTo(endereco);
    }

    @Test
    void findByIdTest(){
        Endereco endereco = getEndereco();

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Optional<Endereco> enderecoResponse = enderecoRepository.findById(1L);

        assertThat(enderecoResponse)
                .isNotNull()
                .isPresent();
    }

    @Test
    void findByIdNotFoundTest() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Endereco> enderecoResponse = enderecoRepository.findById(1L);

        assertFalse(enderecoResponse.isPresent());
    }

    @Test
    void findAllTest(){
        when(enderecoRepository.findAll()).thenReturn(getEnderecoList());

        List<Endereco> enderecoList = enderecoRepository.findAll();

        assertThat(enderecoList)
                .isNotNull()
                .isEqualTo(getEnderecoList());
    }

    @Test
    void deleteByIdTest(){
        Endereco endereco = getEndereco();

        doNothing().when(enderecoRepository).deleteById(any());

        enderecoRepository.deleteById(endereco.id());

        verify(enderecoRepository, times(1)).deleteById(any());
    }
}