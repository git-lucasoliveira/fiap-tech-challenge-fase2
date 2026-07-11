package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.TipoUsuarioView;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.TipoUsuario;
import com.fiap.techchallenge.domain.repository.TipoUsuarioRepository;
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

import static com.fiap.techchallenge.utils.UtilsTipoUsuarioTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TipoUsuarioServiceTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @InjectMocks
    private TipoUsuarioService tipoUsuarioService;

    @Test
    void criarTest() {
        when(tipoUsuarioRepository.save(any())).thenReturn(getTipoUsuario());

        TipoUsuarioView tipoUsuarioResponse = tipoUsuarioService.criar(getCreateTipoUsuarioCommand());

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isInstanceOf(TipoUsuarioView.class)
        ;

        assertThat(tipoUsuarioResponse.id())
                .isNotNull();
    }

    @Test
    void buscarPorIdTest(){
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(getTipoUsuario()));

        TipoUsuarioView tipoUsuarioResponse = tipoUsuarioService.buscarPorId(1L);

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isInstanceOf(TipoUsuarioView.class)
                .isEqualTo(getTipoUsuarioView())
        ;
    }

    @Test
    void buscarPorIdNotFoundTest(){
        when(tipoUsuarioRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> tipoUsuarioService.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("TipoUsuario com id 1 não encontrado.");

        verify(tipoUsuarioRepository, times(1)).findById(1L);
    }

    @Test
    void listarTest(){
        when(tipoUsuarioRepository.findAll()).thenReturn(getTipoUsuarioList());

        List<TipoUsuarioView> tipoUsuarioResponse = tipoUsuarioService.listar();

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isInstanceOf(Collection.class)
                .isEqualTo(getTipoUsuarioViewList());
    }

    @Test
    void atualizarTest(){
        when(tipoUsuarioRepository.findById(any())).thenReturn(Optional.of(getTipoUsuario()));
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(getTipoUsuarioUpdated());

        TipoUsuarioView tipoUsuarioResponse = tipoUsuarioService.atualizar(1L, getUpdateTipoUsuarioCommand());

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isInstanceOf(TipoUsuarioView.class)
                .isEqualTo(getTipoUsuarioViewUpdated());

    }

    @Test
    void atualizarIdNotFoundTest(){
        when(tipoUsuarioRepository.findById(any())).thenReturn(Optional.empty());


        assertThatThrownBy(
                () -> tipoUsuarioService.atualizar(1L, getUpdateTipoUsuarioCommand()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("TipoUsuario com id 1 não encontrado.");

        verify(tipoUsuarioRepository, times(1)).findById(1L);
    }

    @Test
    void deletarTest(){
        when(tipoUsuarioRepository.findById(any())).thenReturn(Optional.of(getTipoUsuario()));
        doNothing().when(tipoUsuarioRepository).deleteById(any());

        tipoUsuarioService.deletar(1L);

        verify(tipoUsuarioRepository, times(1)).deleteById(any());
    }

}
