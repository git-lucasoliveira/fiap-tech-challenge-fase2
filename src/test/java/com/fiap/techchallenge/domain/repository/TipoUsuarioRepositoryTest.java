package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.model.TipoUsuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsTipoUsuarioTest.getTipoUsuario;
import static com.fiap.techchallenge.utils.UtilsTipoUsuarioTest.getTipoUsuarioList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TipoUsuarioRepositoryTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Test
    void saveTest(){
        TipoUsuario tipoUsuario = getTipoUsuario();

        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(tipoUsuario);

        TipoUsuario tipoUsuarioResponse = tipoUsuarioRepository.save(tipoUsuario);

        assertThat(tipoUsuarioResponse).isNotNull().isEqualTo(tipoUsuario);
    }

    @Test
    void findByIdTest(){
        TipoUsuario tipoUsuario = getTipoUsuario();

        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(tipoUsuario));

        Optional<TipoUsuario> tipoUsuarioResponse = tipoUsuarioRepository.findById(1L);

        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isPresent();
    }

    @Test
    void findByIdNotFoundTest() {
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TipoUsuario> tipoUsuarioResponse = tipoUsuarioRepository.findById(1L);

        assertFalse(tipoUsuarioResponse.isPresent());
    }

    @Test
    void findAllTest(){
        when(tipoUsuarioRepository.findAll()).thenReturn(getTipoUsuarioList());

        List<TipoUsuario> tipoUsuarioList = tipoUsuarioRepository.findAll();

        assertThat(tipoUsuarioList)
                .isNotNull()
                .isEqualTo(getTipoUsuarioList());
    }

    @Test
    void deleteByIdTest(){
        TipoUsuario tipoUsuario = getTipoUsuario();

        doNothing().when(tipoUsuarioRepository).deleteById(any());

        tipoUsuarioRepository.deleteById(tipoUsuario.id());

        verify(tipoUsuarioRepository, times(1)).deleteById(any());
    }
}
