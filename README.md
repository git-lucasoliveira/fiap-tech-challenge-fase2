# FIAP Tech Challenge - Fase 2

Estrutura inicial com **Clean Architecture** usando Spring Boot e Java 21.

## Camadas

- `domain`: regras de negocio e contratos (sem dependencia de framework)
- `application`: casos de uso e DTOs de aplicacao
- `infrastructure`: persistencia e integracoes externas
- `presentation`: controladores HTTP e contratos de entrada/saida

## Estrutura

```text
src/main/java/com/fiap/techchallenge
├── application
├── domain
├── infrastructure
└── presentation
```

## Endpoints iniciais

- `GET /api/health` - health check da aplicacao
- `GET /api/users` - lista usuarios
- `POST /api/users` - cria usuario

Exemplo de payload para `POST /api/users`:

```json
{
  "nome": "Maria Silva",
  "email": "maria@fiap.com",
  "login": "maria.silva",
  "senha": "senhaSegura123",
  "fkTipoUsuario": 1,
  "enderecoId": null
}
```

## Rodando localmente

```bash
mvn spring-boot:run
```

## Rodando com Docker Compose

```bash
docker compose up --build
```