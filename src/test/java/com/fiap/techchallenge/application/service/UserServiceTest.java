package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.UserView;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.domain.repository.TipoUsuarioRepository;
import com.fiap.techchallenge.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge.utils.UtilsTipoUsuarioTest.getTipoUsuario;
import static com.fiap.techchallenge.utils.UtilsUserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @InjectMocks
    private UserService userService;

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
    void criarUsuarioTest(){

        when(tipoUsuarioRepository.findById(any(Long.class))).thenReturn(Optional.of(getTipoUsuario()));
        when(userRepository.save(any(User.class))).thenReturn(getUser());

        UserView userResponse = userService.criar(getCreateUserCommand());

        assertThat(userResponse)
                .isNotNull()
                .isInstanceOf(UserView.class)
        ;

        assertThat(userResponse.id())
                .isNotNull();
    }

    @Test
    void criarUsuarioIdTipoUsuarioNotFountTest(){
        when(tipoUsuarioRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> userService.criar(getCreateUserCommand()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("TipoUsuario com id 1 não encontrado.");

        verify(tipoUsuarioRepository, times(1)).findById(1L);
    }

    @Test
    void criarUsuarioIdTipoUsuarioNullTest(){
        when(tipoUsuarioRepository.findById(null)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> userService.criar(getCreateUserCommandWithFkNull()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("TipoUsuario com id null não encontrado.");

        verify(tipoUsuarioRepository, times(0)).findById(1L);
    }

    @Test
    void buscarPorIdTest(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(getUser()));

        UserView userResponse = userService.buscarPorId(1L);

        assertThat(userResponse)
                .isNotNull()
                .isInstanceOf(UserView.class)
                .isEqualTo(getUserView())
        ;
    }

    @Test
    void buscarPorIdNotFoundTest(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> userService.buscarPorId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário com id 1 não encontrado.");

        verify(userRepository, times(1)).findById(1L);

    }

    @Test
    void listarTest(){
        when(userRepository.findAll()).thenReturn(getUserList());

        List<UserView> userResponse = userService.listar();

        assertThat(userResponse)
                .isNotNull()
                .isInstanceOf(Collection.class)
                .isEqualTo(getUserViewList())
        ;
    }

    @Test
    void atualizarTest(){
        when(tipoUsuarioRepository.findById(any(Long.class))).thenReturn(Optional.of(getTipoUsuario()));
        when(userRepository.findById(any())).thenReturn(Optional.of(getUser()));
        when(userRepository.save(any(User.class))).thenReturn(getUserUpdated());

        UserView userResponse = userService.atualizar(1L, getUpdateUserCommand());

        assertThat(userResponse)
                .isNotNull()
                .isInstanceOf(UserView.class)
                .isEqualTo(getUserViewUpdated());

    }

    @Test
    void atualizarIdNotFoundTest(){
        when(tipoUsuarioRepository.findById(any(Long.class))).thenReturn(Optional.of(getTipoUsuario()));
        when(userRepository.findById(any())).thenReturn(Optional.empty());


        assertThatThrownBy(
                () -> userService.atualizar(1L, getUpdateUserCommand()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário com id 1 não encontrado.");

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void atualizarIdTipoUsuarioNotFoundTest(){
        when(userRepository.findById(any())).thenReturn(Optional.of(getUser()));
        when(tipoUsuarioRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        assertThatThrownBy(
                () -> userService.atualizar(1L, getUpdateUserCommand()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("TipoUsuario com id 1 não encontrado.");

        verify(userRepository, times(1)).findById(1L);
        verify(tipoUsuarioRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTest(){
        when(userRepository.findById(any())).thenReturn(Optional.of(getUser()));
        doNothing().when(userRepository).deleteById(any());

        userService.deletar(any());

        verify(userRepository, times(1)).deleteById(any());
    }
}
