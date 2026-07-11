# FIAP Tech Challenge - Fase 2

Projeto desenvolvido para o **Tech Challenge - Fase 2 da FIAP**, utilizando **Java 21**, **Spring Boot** e uma estrutura baseada em **Clean Architecture**.

A aplicação possui endpoints REST para gerenciamento de usuários, tipos de usuário, endereços, restaurantes e itens do cardápio.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker Compose
- Maven
- Swagger / OpenAPI

## Arquitetura do projeto

O projeto segue uma organização inspirada em **Clean Architecture**, separando responsabilidades em camadas.

## Camadas

- `domain`: regras de negócio, modelos de domínio e contratos de repositório
- `application`: serviços de aplicação, casos de uso e DTOs internos
- `infrastructure`: persistência, entidades JPA, adapters e repositórios Spring Data
- `presentation`: controllers REST e DTOs de entrada e saída da API

## Estrutura

```text
src/main/java/com/fiap/techchallenge
├── application
├── domain
├── infrastructure
└── presentation
```

## Funcionalidades

- Cadastro, listagem, busca, atualização e exclusão de usuários
- Cadastro, listagem, busca, atualização e exclusão de tipos de usuário
- Associação de usuário a um tipo de usuário
- Cadastro, listagem, busca, atualização e exclusão de endereços
- Cadastro, listagem, busca, atualização e exclusão de restaurantes
- Cadastro, listagem, busca, atualização e exclusão de itens do cardápio
- Health check da aplicação
- Documentação da API com Swagger/OpenAPI

## Tipos de usuário

A aplicação permite diferenciar usuários por tipo, como por exemplo:

- Cliente
- Dono de Restaurante

O vínculo entre usuário e tipo de usuário é feito no cadastro ou atualização do usuário por meio do campo:

```json
{
  "fkTipoUsuario": 1
}
```

Caso seja informado um tipo de usuário inexistente, a API retorna erro `404`.

## Endpoints principais

### Health Check

```http
GET /api/health
```

### Usuários

```http
GET /api/users
GET /api/users/{id}
POST /api/users
PUT /api/users/{id}
DELETE /api/users/{id}
```

### Tipos de Usuário

```http
GET /api/tipos-usuario
GET /api/tipos-usuario/{id}
POST /api/tipos-usuario
PUT /api/tipos-usuario/{id}
DELETE /api/tipos-usuario/{id}
```

### Endereços

```http
GET /api/enderecos
GET /api/enderecos/{id}
POST /api/enderecos
PUT /api/enderecos/{id}
DELETE /api/enderecos/{id}
```

### Restaurantes

Os endpoints de restaurantes estão disponíveis e documentados no Swagger.

### Itens do Cardápio

Os endpoints de itens do cardápio estão disponíveis e documentados no Swagger.

## Exemplo de payload para cadastro de usuário

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

## Documentação Swagger

Após iniciar a aplicação, a documentação da API pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

Também é possível acessar a especificação OpenAPI em formato JSON:

```text
http://localhost:8080/v3/api-docs
```

## Atualizar collection do Postman com base no contrato

Com a aplicacao rodando, gere ou atualize a collection a partir do OpenAPI:

```powershell
cd "C:\[CAMINHO DO PATH DA SUA APP]\FIAP\Project\fiap-tech-challenge-fase2"
.\update-postman-collection.ps1
```

O script:

- baixa o contrato em `target/openapi.json`
- gera a collection com `openapi-generator`
- atualiza `collections/FIAP-Tech-Challenge.postman_collection.json`
- cria backup automatico da collection anterior

Para validar o fluxo sem alterar arquivos:

```powershell
cd "C:\[CAMINHO DO PATH DA SUA APP]\Project\fiap-tech-challenge-fase2"
.\update-postman-collection.ps1 -DryRun
```

No Swagger estão documentados os seguintes grupos de endpoints:

- Usuários
- Endereços
- Restaurantes
- Tipos de Usuário
- Health Check
- Itens do Cardápio

## Rodando localmente

Para compilar o projeto:

```bash
mvn clean install
```

Para iniciar a aplicação localmente:

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Rodando com Docker Compose

Para subir a aplicação e o banco de dados com Docker Compose:

```bash
docker compose up --build
```

Para parar os containers:

```bash
docker compose down
```

## Banco de dados

O projeto utiliza PostgreSQL como banco de dados.

Ao rodar com Docker Compose, o banco é iniciado automaticamente conforme as configurações definidas no arquivo `docker-compose.yml`.

## Validação do projeto

O projeto foi validado com:

```bash
mvn clean install
```

Também foram testados:

- Inicialização da aplicação na porta `8080`
- Acesso ao Swagger
- CRUD de tipos de usuário
- Associação de usuário com tipo de usuário
- Retorno `404` para tipo de usuário inexistente