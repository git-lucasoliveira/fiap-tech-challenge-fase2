package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.TipoUsuarioCommand;
import com.fiap.techchallenge.application.dto.TipoUsuarioView;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.TipoUsuario;
import com.fiap.techchallenge.domain.repository.TipoUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public TipoUsuarioService(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Transactional
    public TipoUsuarioView criar(TipoUsuarioCommand command) {
        TipoUsuario tipoUsuario = new TipoUsuario(null, command.nome());
        return toView(tipoUsuarioRepository.save(tipoUsuario));
    }

    @Transactional
    public TipoUsuarioView buscarPorId(Long id) {
        return tipoUsuarioRepository.findById(id)
                .map(this::toView)
                .orElseThrow(() -> new ResourceNotFoundException("TipoUsuario", id));
    }

    @Transactional
    public List<TipoUsuarioView> listar() {
        return tipoUsuarioRepository.findAll().stream().map(this::toView).toList();
    }

    @Transactional
    public TipoUsuarioView atualizar(Long id, TipoUsuarioCommand command) {
        tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoUsuario", id));
        TipoUsuario atualizado = new TipoUsuario(id, command.nome());
        return toView(tipoUsuarioRepository.save(atualizado));
    }

    @Transactional
    public void deletar(Long id) {
        tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoUsuario", id));
        tipoUsuarioRepository.deleteById(id);
    }

    private TipoUsuarioView toView(TipoUsuario t) {
        return new TipoUsuarioView(t.id(), t.nome());
    }
}

