package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.EnderecoCommand;
import com.fiap.techchallenge.application.dto.EnderecoView;
import com.fiap.techchallenge.domain.exception.ResourceNotFoundException;
import com.fiap.techchallenge.domain.model.Endereco;
import com.fiap.techchallenge.domain.repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {

        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public EnderecoView criar(EnderecoCommand command) {
        Endereco endereco = new Endereco(null, command.rua(), command.numero(), command.cidade(),
                command.cep(), command.complemento(), command.estado(), command.bairro());
        return toView(enderecoRepository.save(endereco));
    }

    @Transactional
    public EnderecoView buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(this::toView)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço", id));
    }

    @Transactional
    public List<EnderecoView> listar() {

        return enderecoRepository.findAll().stream().map(this::toView).toList();
    }

    @Transactional
    public EnderecoView atualizar(Long id, EnderecoCommand command) {
        enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço", id));
        Endereco atualizado = new Endereco(id, command.rua(), command.numero(), command.cidade(),
                command.cep(), command.complemento(), command.estado(), command.bairro());
        return toView(enderecoRepository.save(atualizado));
    }

    @Transactional
    public void deletar(Long id) {
        enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço", id));
        enderecoRepository.deleteById(id);
    }

    private EnderecoView toView(Endereco e) {
        return new EnderecoView(e.id(), e.rua(), e.numero(), e.cidade(),
                e.cep(), e.complemento(), e.estado(), e.bairro());
    }
}

