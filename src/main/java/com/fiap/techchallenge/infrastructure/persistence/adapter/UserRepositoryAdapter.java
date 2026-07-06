package com.fiap.techchallenge.infrastructure.persistence.adapter;

import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.domain.repository.UserRepository;
import com.fiap.techchallenge.infrastructure.persistence.entity.UserJpaEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.SpringDataUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springRepo;

    public UserRepositoryAdapter(SpringDataUserRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public User save(User user) {
        return toDomain(springRepo.save(toEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return springRepo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepo.deleteById(id);
    }

    private UserJpaEntity toEntity(User u) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(u.id());
        entity.setNome(u.nome());
        entity.setEmail(u.email());
        entity.setLogin(u.login());
        entity.setSenha(u.senha());
        entity.setFkTipoUsuario(u.fkTipoUsuario());
        entity.setEnderecoId(u.enderecoId());
        entity.setDataUltimaAlteracao(u.dataUltimaAlteracao());
        return entity;
    }

    private User toDomain(UserJpaEntity e) {
        return new User(e.getId(), e.getNome(), e.getEmail(), e.getLogin(),
                e.getSenha(), e.getFkTipoUsuario(), e.getEnderecoId(), e.getDataUltimaAlteracao());
    }
}

