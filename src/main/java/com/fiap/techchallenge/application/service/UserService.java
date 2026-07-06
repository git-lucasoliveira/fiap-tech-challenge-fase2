package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.CreateUserCommand;
import com.fiap.techchallenge.application.dto.UpdateUserCommand;
import com.fiap.techchallenge.application.dto.UserView;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserView criar(CreateUserCommand command) {
        User user = new User(null, command.nome(), command.email(), command.login(),
                command.senha(), command.fkTipoUsuario(), command.enderecoId(), LocalDateTime.now());
        return toView(userRepository.save(user));
    }

    @Transactional
    public UserView buscarPorId(Long id) {
        return userRepository.findById(id)
                .map(this::toView)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }

    @Transactional
    public List<UserView> listar() {
        return userRepository.findAll().stream().map(this::toView).toList();
    }

    @Transactional
    public UserView atualizar(Long id, UpdateUserCommand command) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        User atualizado = new User(id, command.nome(), command.email(), command.login(),
                command.senha(), command.fkTipoUsuario(), command.enderecoId(), LocalDateTime.now());
        return toView(userRepository.save(atualizado));
    }

    @Transactional
    public void deletar(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        userRepository.deleteById(id);
    }

    private UserView toView(User u) {
        return new UserView(u.id(), u.nome(), u.email(), u.login(),
                u.fkTipoUsuario(), u.enderecoId(), u.dataUltimaAlteracao());
    }
}

