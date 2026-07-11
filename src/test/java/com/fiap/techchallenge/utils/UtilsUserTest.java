package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.application.dto.CreateUserCommand;
import com.fiap.techchallenge.application.dto.UpdateUserCommand;
import com.fiap.techchallenge.application.dto.UserView;
import com.fiap.techchallenge.domain.model.User;
import com.fiap.techchallenge.infrastructure.persistence.entity.UserJpaEntity;
import com.fiap.techchallenge.presentation.dto.CreateUserRequest;
import com.fiap.techchallenge.presentation.dto.UpdateUserRequest;
import com.fiap.techchallenge.presentation.dto.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public abstract class UtilsUserTest {

    private static final LocalDateTime data = LocalDateTime.now();

    public static User getUser() {
        return new User(
                1L,
                "joana",
                "joana@gmail.com",
                "joana123",
                "123456",
                1L,
                1L,
                data
        );
    }

    public static User getUserUpdated() {
        return new User(
                1L,
                "joana maria",
                "joanamaria@gmail.com",
                "joana123",
                "123456",
                1L,
                1L,
                data
        );
    }

    public static List<User> getUserList(){
        return List.of(
                new User(
                        1L,
                        "joana",
                        "joana@gmail.com",
                        "joana123",
                        "123456",
                        1L,
                        1L,
                        data
                )
        );
    }

    public static CreateUserCommand getCreateUserCommand() {
        return new CreateUserCommand(
                "joana",
                "joana@gmail.com",
                "joana123",
                "123456",
                1L,
                1L
        );
    }

    public static CreateUserCommand getCreateUserCommandWithFkNull() {
        return new CreateUserCommand(
                "joana",
                "joana@gmail.com",
                "joana123",
                "123456",
                null,
                1L
        );
    }

    public static UpdateUserCommand getUpdateUserCommand() {
        return new UpdateUserCommand(
                "joana maria",
                "joanamaria@gmail.com",
                "joana123",
                "123456",
                1L,
                1L
        );
    }

    public static UserView getUserView(){
        return new UserView(
                1L,
                "joana",
                "joana@gmail.com",
                "joana123",
                1L,
                1L,
                data
        );
    }

    public static UserView getUserViewUpdated(){
        return new UserView(
                1L,
                "joana maria",
                "joanamaria@gmail.com",
                "joana123",
                1L,
                1L,
                data
        );
    }

    public static List<UserView> getUserViewList(){
        return List.of(
                new UserView(
                        1L,
                        "joana",
                        "joana@gmail.com",
                        "joana123",
                        1L,
                        1L,
                        data
                )
        );
    }

    public static UserJpaEntity getUserJpaEntity() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setNome("joana");
        entity.setEmail("joana@gmail.com");
        entity.setLogin("joana123");
        entity.setSenha("123456");
        entity.setFkTipoUsuario(1L);
        entity.setEnderecoId(1L);
        entity.setDataUltimaAlteracao(data);
        return entity;
    }

    public static List<UserJpaEntity> getUserJpaEntityList() {
        return List.of(getUserJpaEntity());
    }

    public static UserResponse getUserResponse() {
        return new UserResponse(
                1L,
                "joana",
                "joana@gmail.com",
                "joana123",
                1L,
                1L,
                data
        );
    }

    public static UserResponse getUserResponseUpdated() {
        return new UserResponse(
                1L,
                "joana maria",
                "joanamaria@gmail.com",
                "joana123",
                1L,
                1L,
                data
        );
    }

    public static List<UserResponse> getUserResponseList() {
        return List.of(getUserResponse());
    }

    public static CreateUserRequest getCreateUserRequest() {
        return new CreateUserRequest(
                "joana",
                "joana@gmail.com",
                "joana123",
                "123456",
                1L,
                1L
        );
    }

    public static UpdateUserRequest getUpdateUserRequest() {
        return new UpdateUserRequest(
                "joana maria",
                "joanamaria@gmail.com",
                "joana123",
                "123456",
                1L,
                1L
        );
    }
}