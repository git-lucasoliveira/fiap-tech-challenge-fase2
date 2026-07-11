package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.EnderecoView;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.Endereco;
import com.fiap.techchallenge.domain.repository.EnderecoRepository;
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

import static com.fiap.techchallenge.utils.UtilsEnderecoTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {
    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    void criarTest() {
        when(enderecoRepository.save(any())).thenReturn(getEndereco());

        EnderecoView enderecoView = enderecoService.criar(getCreateEnderecoCommand());

        assertThat(enderecoView)
                .isNotNull()
                .isInstanceOf(EnderecoView.class);

        assertThat(enderecoView.id())
                .isNotNull();
    }

    @Test
    void buscarPorIdTest(){
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(getEndereco()));

        EnderecoView enderecoResponse = enderecoService.buscarPorId(1L);

        assertThat(enderecoResponse)
                .isNotNull()
                .isInstanceOf(EnderecoView.class)
                .isEqualTo(getEnderecoView())
        ;
    }

    @Test
    void buscarPorIdNotFoundTest(){
        when(enderecoRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> enderecoService.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Endereço com id 1 não encontrado.");

        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    void listarTest(){
        when(enderecoRepository.findAll()).thenReturn(getEnderecoList());

        List<EnderecoView> enderecoResponse = enderecoService.listar();

        assertThat(enderecoResponse)
                .isNotNull()
                .isInstanceOf(Collection.class)
                .isEqualTo(getEnderecoViewList());
    }

    @Test
    void atualizarTest(){
        when(enderecoRepository.findById(any())).thenReturn(Optional.of(getEndereco()));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(getEnderecoUpdated());

        EnderecoView enderecoResponse = enderecoService.atualizar(1L, getUpdateEnderecoCommand());

        assertThat(enderecoResponse)
                .isNotNull()
                .isInstanceOf(EnderecoView.class)
                .isEqualTo(getEnderecoViewUpdated());

    }

    @Test
    void atualizarIdNotFoundTest(){
        when(enderecoRepository.findById(any())).thenReturn(Optional.empty());


        assertThatThrownBy(
                () -> enderecoService.atualizar(1L, getUpdateEnderecoCommand()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Endereço com id 1 não encontrado.");

        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    void deletarTest(){
        when(enderecoRepository.findById(any())).thenReturn(Optional.of(getEndereco()));
        doNothing().when(enderecoRepository).deleteById(any());

        enderecoService.deletar(1L);

        verify(enderecoRepository, times(1)).deleteById(any());
    }

}
