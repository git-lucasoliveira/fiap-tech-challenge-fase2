package com.fiap.techchallenge.utils;

import com.fiap.techchallenge.application.dto.EnderecoCommand;
import com.fiap.techchallenge.application.dto.EnderecoView;
import com.fiap.techchallenge.domain.model.Endereco;
import com.fiap.techchallenge.infrastructure.persistence.entity.EnderecoJpaEntity;
import com.fiap.techchallenge.presentation.dto.EnderecoRequest;
import com.fiap.techchallenge.presentation.dto.EnderecoResponse;

import java.util.List;

public abstract class UtilsEnderecoTest {

    public static Endereco getEndereco() {
        return new Endereco(
                1L,
                "Rua A",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static Endereco getEnderecoUpdated() {
        return new Endereco(
                1L,
                "Rua updated",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static List<Endereco> getEnderecoList() {
        return List.of(getEndereco());
    }

    public static EnderecoCommand getCreateEnderecoCommand() {
        return new EnderecoCommand(
                "Rua A",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static EnderecoView getEnderecoView(){
        return new EnderecoView(
                1L,
                "Rua A",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static List<EnderecoView> getEnderecoViewList(){
        return List.of(getEnderecoView());
    }

    public static EnderecoView getEnderecoViewUpdated(){
        return new EnderecoView(
                1L,
                "Rua updated",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static EnderecoCommand getUpdateEnderecoCommand(){
        return new EnderecoCommand(
                "Rua updated",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static EnderecoJpaEntity getEnderecoJpaEntity() {
        EnderecoJpaEntity enderecoJpaEntity = new EnderecoJpaEntity();
        enderecoJpaEntity.setId(1L);
        enderecoJpaEntity.setRua("Rua A");
        enderecoJpaEntity.setNumero("123");
        enderecoJpaEntity.setBairro("bairro");
        enderecoJpaEntity.setCidade("Bairro B");
        enderecoJpaEntity.setEstado("12345-678");
        enderecoJpaEntity.setCep("Cidade C");
        enderecoJpaEntity.setComplemento("Estado D");
        return enderecoJpaEntity;
    }

    public static List<EnderecoJpaEntity> getEnderecoJpaEntityList() {
        return List.of(getEnderecoJpaEntity());
    }

    public static EnderecoResponse getEnderecoResponse() {
        return new EnderecoResponse(
                1L,
                "Rua A",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static List<EnderecoResponse> getEnderecoResponseList() {
        return List.of(getEnderecoResponse());
    }

    public static EnderecoRequest getEnderecoRequest() {
        return new EnderecoRequest(
                "Rua A",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }

    public static EnderecoRequest getEnderecoRequestUpdate() {
        return new EnderecoRequest(
                "Rua updated",
                "123",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345-678",
                "bairro"
        );
    }
}
