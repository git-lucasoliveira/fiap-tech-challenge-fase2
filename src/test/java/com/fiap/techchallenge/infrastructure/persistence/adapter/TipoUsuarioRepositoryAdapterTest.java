package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.TipoUsuario;
import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.infrastructure.persistence.entity.TipoUsuarioJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataTipoUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsTipoUsuarioTest.*;
import static com.fiap.techchallenge.utils.UtilsUserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TipoUsuarioRepositoryAdapterTest {
    @Mock
    private SpringDataTipoUsuarioRepository springDataTipoUsuarioRepository;

    @InjectMocks
    private TipoUsuarioRepositoryAdapter tipoUsuarioRepositoryAdapterTest;

    @Test
    void saveTest() {
        TipoUsuario tipoUsuario = getTipoUsuario();

        when(springDataTipoUsuarioRepository.save(any(TipoUsuarioJpaEntity.class))).thenReturn(getTipoUsuarioJpaEntity());

        TipoUsuario tipoUsuarioResponse = tipoUsuarioRepositoryAdapterTest.save(tipoUsuario);

        assertEquals(tipoUsuario.id(), tipoUsuarioResponse.id());
        assertThat(tipoUsuarioResponse)
                .isNotNull()
                .isEqualTo(tipoUsuario);
    }

    @Test
    void findByIdTest() {
        when(springDataTipoUsuarioRepository.findById(anyLong())).thenReturn(Optional.of(getTipoUsuarioJpaEntity()));

        Optional<TipoUsuario> tipoUsuario = tipoUsuarioRepositoryAdapterTest.findById(1L);

        assertThat(tipoUsuario)
                .isNotNull()
                .isEqualTo(Optional.of(getTipoUsuario()));
    }

    @Test
    void findAll(){
        when(springDataTipoUsuarioRepository.findAll()).thenReturn(getTipoUsuarioJpaEntityList());

        List<TipoUsuario> tipoUsuarioList = tipoUsuarioRepositoryAdapterTest.findAll();

        assertThat(tipoUsuarioList)
                .isNotNull()
                .isEqualTo(getTipoUsuarioList())
                .isInstanceOf(Collection.class);
    }

    @Test
    void deleteById(){
        doNothing().when(springDataTipoUsuarioRepository).deleteById(any());

        tipoUsuarioRepositoryAdapterTest.deleteById(1L);

        verify(springDataTipoUsuarioRepository, times(1)).deleteById(any());
    }
}