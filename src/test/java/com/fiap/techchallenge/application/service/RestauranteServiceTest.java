package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.CreateRestauranteCommand;
import com.fiap.techchallenge.application.dto.RestauranteView;
import com.fiap.techchallenge.application.dto.UpdateRestauranteCommand;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.Restaurante;
import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.domain.repository.RestauranteRepository;
import com.fiap.techchallenge.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestauranteService restauranteService;

    private User dono() {
        return new User(1L, "Ana", "ana@email.com", "ana", "123", 1L, null, LocalDateTime.now());
    }

    private Restaurante restaurante(Long id) {
        return new Restaurante(id, "Cantina", "Italiana", "10h-22h", 1L, 5L);
    }

    @Test
    void criarComDonoExistenteSalvaERetornaView() {
        CreateRestauranteCommand command = new CreateRestauranteCommand("Cantina", "Italiana", "10h-22h", 1L, 5L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(dono()));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante(10L));

        RestauranteView view = restauranteService.criar(command);

        assertEquals(10L, view.id());
        assertEquals("Cantina", view.nome());
        assertEquals(1L, view.donoId());
        verify(restauranteRepository).save(any(Restaurante.class));
    }

    @Test
    void criarComDonoInexistenteLancaExcecao() {
        CreateRestauranteCommand command = new CreateRestauranteCommand("Cantina", "Italiana", "10h-22h", 99L, 5L);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.criar(command));
        verify(restauranteRepository, never()).save(any(Restaurante.class));
    }

    @Test
    void buscarPorIdExistenteRetornaView() {
        when(restauranteRepository.findById(10L)).thenReturn(Optional.of(restaurante(10L)));

        RestauranteView view = restauranteService.buscarPorId(10L);

        assertEquals(10L, view.id());
        assertEquals("Cantina", view.nome());
    }

    @Test
    void buscarPorIdInexistenteLancaExcecao() {
        when(restauranteRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.buscarPorId(10L));
    }

    @Test
    void listarRetornaTodos() {
        when(restauranteRepository.findAll()).thenReturn(List.of(restaurante(10L), restaurante(11L)));

        List<RestauranteView> views = restauranteService.listar();

        assertEquals(2, views.size());
    }

    @Test
    void listarPorDonoRetornaFiltrados() {
        when(restauranteRepository.findByDonoId(1L)).thenReturn(List.of(restaurante(10L)));

        List<RestauranteView> views = restauranteService.listarPorDono(1L);

        assertEquals(1, views.size());
        assertEquals(1L, views.get(0).donoId());
    }

    @Test
    void atualizarComSucessoRetornaView() {
        UpdateRestauranteCommand command = new UpdateRestauranteCommand("Cantina Nova", "Italiana", "11h-23h", 1L, 5L);
        when(restauranteRepository.findById(10L)).thenReturn(Optional.of(restaurante(10L)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(dono()));
        when(restauranteRepository.save(any(Restaurante.class)))
                .thenReturn(new Restaurante(10L, "Cantina Nova", "Italiana", "11h-23h", 1L, 5L));

        RestauranteView view = restauranteService.atualizar(10L, command);

        assertEquals("Cantina Nova", view.nome());
        verify(restauranteRepository).save(any(Restaurante.class));
    }

    @Test
    void atualizarRestauranteInexistenteLancaExcecao() {
        UpdateRestauranteCommand command = new UpdateRestauranteCommand("Cantina Nova", "Italiana", "11h-23h", 1L, 5L);
        when(restauranteRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.atualizar(10L, command));
        verify(restauranteRepository, never()).save(any(Restaurante.class));
    }

    @Test
    void atualizarComDonoInexistenteLancaExcecao() {
        UpdateRestauranteCommand command = new UpdateRestauranteCommand("Cantina Nova", "Italiana", "11h-23h", 99L, 5L);
        when(restauranteRepository.findById(10L)).thenReturn(Optional.of(restaurante(10L)));
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.atualizar(10L, command));
        verify(restauranteRepository, never()).save(any(Restaurante.class));
    }

    @Test
    void deletarComSucessoRemove() {
        when(restauranteRepository.findById(10L)).thenReturn(Optional.of(restaurante(10L)));

        restauranteService.deletar(10L);

        verify(restauranteRepository).deleteById(10L);
    }

    @Test
    void deletarInexistenteLancaExcecao() {
        when(restauranteRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restauranteService.deletar(10L));
        verify(restauranteRepository, never()).deleteById(10L);
    }
}
