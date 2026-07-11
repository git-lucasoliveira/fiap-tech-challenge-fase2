package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.application.dto.TipoUsuarioCommand;
import com.fiap.techchallenge.application.dto.TipoUsuarioView;
import com.fiap.techchallenge.domain.model.TipoUsuario;
import com.fiap.techchallenge.infrastructure.persistence.entity.TipoUsuarioJpaEntity;
import com.fiap.techchallenge.presentation.dto.TipoUsuarioRequest;
import com.fiap.techchallenge.presentation.dto.TipoUsuarioResponse;

import java.util.List;

public abstract class UtilsTipoUsuarioTest {

    public static TipoUsuario getTipoUsuario() {
        return new TipoUsuario(
                1L,
                "cliente"
        );
    }

    public static List<TipoUsuario> getTipoUsuarioList() {
        return List.of(
                new TipoUsuario(
                        1L,
                        "cliente"
                )
        );
    }

    public static TipoUsuarioCommand getCreateTipoUsuarioCommand() {
        return new TipoUsuarioCommand(
                "cliente"
        );
    }

    public static TipoUsuarioView getTipoUsuarioView() {
        return new TipoUsuarioView(
                1L,
                "cliente"
        );
    }

    public static List<TipoUsuarioView> getTipoUsuarioViewList() {
        return List.of(
                new TipoUsuarioView(
                        1L,
                        "cliente"
                )
        );
    }

    public static TipoUsuario getTipoUsuarioUpdated(){
        return new TipoUsuario(
                1L,
                "cliente atualizado"
        );
    }

    public static TipoUsuarioView getTipoUsuarioViewUpdated(){
        return new TipoUsuarioView(
                1L,
                "cliente atualizado"
        );
    }

    public static TipoUsuarioCommand getUpdateTipoUsuarioCommand() {
        return new TipoUsuarioCommand(
                "cliente atualizado"
        );
    }

    public static TipoUsuarioJpaEntity getTipoUsuarioJpaEntity() {
        TipoUsuarioJpaEntity tipoUsuarioJpaEntity = new TipoUsuarioJpaEntity();
        tipoUsuarioJpaEntity.setId(1L);
        tipoUsuarioJpaEntity.setNome("cliente");
        return tipoUsuarioJpaEntity;
    }

    public static List<TipoUsuarioJpaEntity> getTipoUsuarioJpaEntityList() {
        return List.of(getTipoUsuarioJpaEntity());
    }

    public static TipoUsuarioRequest getTipoUsuarioRequest() {
        return new TipoUsuarioRequest(
                "cliente"
        );
    }

    public static TipoUsuarioRequest getTipoUsuarioRequestUpdate() {
        return new TipoUsuarioRequest(
                "cliente atualizado"
        );
    }

    public static TipoUsuarioResponse getTipoUsuarioResponse() {
        return new TipoUsuarioResponse(
                1L,
                "cliente"
        );
    }

    public static TipoUsuarioResponse getTipoUsuarioResponseUpdate() {
        return new TipoUsuarioResponse(
                1L,
                "cliente atualizado"
        );
    }

    public static List<TipoUsuarioResponse> getTipoUsuarioResponseList(){
        return List.of(
                getTipoUsuarioResponse()
        );
    }
}
