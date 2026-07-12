# FIAP Tech Challenge - Fase 2

Projeto desenvolvido para o **Tech Challenge - Fase 2 da FIAP**, pela equipe 21, utilizando **Java 21**, **Spring Boot** e uma a arquitetura baseada em **Clean Architecture**.

A aplicação é uma API REST para gestão de restaurantes, permitindo o cadastro, o gerenciamento de usuários, tipos de usuário, endereços, restaurantes e itens do cardápio.

## Objetivo do projeto

O objetivo é disponibilizar uma API backend para apoiar a gestão de restaurantes, permitindo:

- Diferenciar usuários entre **Cliente** e **Dono de Restaurante**.
- Cadastrar e gerenciar tipos de usuário.
- Cadastrar e gerenciar usuários.
- Cadastrar e gerenciar endereços.
- Cadastrar e gerenciar restaurantes.
- Cadastrar e gerenciar itens do cardápio.
- Associar restaurantes a usuários responsáveis.
- Associar itens do cardápio a restaurantes.
- Consultar e testar os endpoints.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- Docker
- Docker Compose
- Swagger / OpenAPI
- Postman
- JUnit

## Arquitetura do projeto

O projeto segue uma organização inspirada em **Clean Architecture**, separando responsabilidades em camadas.

Essa estrutura permite reduzir o acoplamento entre regras de negócio, frameworks, banco de dados e camada de apresentação, facilitando manutenção, testes e evolução do sistema.

## Camadas

- `domain`: contém os modelos de domínio, regras principais e contratos de repositório.
- `application`: contém os serviços de aplicação, casos de uso e DTOs internos.
- `infrastructure`: contém a implementação de persistência, entidades JPA, adapters e repositórios Spring Data.
- `presentation`: contém os controllers REST e os DTOs de entrada e saída da API.

## Estrutura de pastas

```text
src/main/java/com/fiap/techchallenge
├── application
│   ├── dto
│   └── service
├── domain
│   ├── exception
│   ├── model
│   └── repository
├── infrastructure
│   └── persistence
│       ├── adapter
│       ├── entity
│       └── repository
└── presentation
    ├── controller
    └── dto
```

## Funcionalidades

- CRUD de usuários.
- CRUD de tipos de usuário.
- Associação de usuário a um tipo de usuário.
- CRUD de endereços.
- CRUD de restaurantes.
- Associação de restaurante a um usuário dono.
- CRUD de itens do cardápio.
- Associação de item do cardápio a um restaurante.
- Registro de disponibilidade do item para consumo no local.
- Registro do caminho de armazenamento da foto do prato.
- Health check da aplicação.
- Documentação da API com Swagger/OpenAPI.
- Collections do Postman para teste dos endpoints.
- Testes automatizados.

## Tipos de usuário

A aplicação permite distinguir usuários por tipo, como por exemplo:

- Cliente
- Dono de Restaurante

O vínculo entre usuário e tipo de usuário é feito no cadastro ou atualização do usuário por meio do campo `fkTipoUsuario`.

Exemplo:

```json
{
  "fkTipoUsuario": 1
}
```

Caso seja informado um tipo de usuário inexistente, a API retorna erro `404`.

## Endpoints da API

A URL base da aplicação local é:

```text
http://localhost:8080
```

### Health Check

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/health` | Verifica se a aplicação está em funcionamento. |

### Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/users` | Cadastra um novo usuário. |
| GET | `/api/users` | Lista todos os usuários. |
| GET | `/api/users/{id}` | Busca um usuário pelo ID. |
| PUT | `/api/users/{id}` | Atualiza um usuário existente. |
| DELETE | `/api/users/{id}` | Remove um usuário pelo ID. |

#### Exemplo de payload para cadastro de usuário

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

#### Exemplo de payload para atualização de usuário

```json
{
  "nome": "Maria Silva Atualizada",
  "email": "maria.atualizada@fiap.com",
  "login": "maria.silva",
  "senha": "novaSenha123",
  "fkTipoUsuario": 1,
  "enderecoId": null
}
```

### Tipos de Usuário

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/tipos-usuario` | Cadastra um novo tipo de usuário. |
| GET | `/api/tipos-usuario` | Lista todos os tipos de usuário. |
| GET | `/api/tipos-usuario/{id}` | Busca um tipo de usuário pelo ID. |
| PUT | `/api/tipos-usuario/{id}` | Atualiza um tipo de usuário existente. |
| DELETE | `/api/tipos-usuario/{id}` | Remove um tipo de usuário pelo ID. |

#### Exemplo de payload para tipo de usuário

```json
{
  "nome": "Cliente"
}
```

### Endereços

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/enderecos` | Cadastra um novo endereço. |
| GET | `/api/enderecos` | Lista todos os endereços. |
| GET | `/api/enderecos/{id}` | Busca um endereço pelo ID. |
| PUT | `/api/enderecos/{id}` | Atualiza um endereço existente. |
| DELETE | `/api/enderecos/{id}` | Remove um endereço pelo ID. |

#### Exemplo de payload para endereço

```json
{
  "rua": "Rua das Flores",
  "numero": "123",
  "cidade": "São Paulo",
  "cep": "01000-000",
  "complemento": "Apto 101",
  "estado": "SP",
  "bairro": "Centro"
}
```

### Restaurantes

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/restaurantes` | Cadastra um novo restaurante. |
| GET | `/api/restaurantes` | Lista todos os restaurantes. |
| GET | `/api/restaurantes/{id}` | Busca um restaurante pelo ID. |
| PUT | `/api/restaurantes/{id}` | Atualiza um restaurante existente. |
| DELETE | `/api/restaurantes/{id}` | Remove um restaurante pelo ID. |

#### Exemplo de payload para cadastro de restaurante

```json
{
  "nome": "Restaurante do João",
  "tipoCozinha": "Brasileira",
  "horarioFuncionamento": "Segunda a sexta, das 11h às 22h",
  "donoId": 2,
  "enderecoId": null
}
```

#### Exemplo de payload para atualização de restaurante

```json
{
  "nome": "Restaurante do João Atualizado",
  "tipoCozinha": "Italiana",
  "horarioFuncionamento": "Todos os dias, das 10h às 23h",
  "donoId": 2,
  "enderecoId": null
}
```

### Itens do Cardápio

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/itens-cardapio` | Cadastra um novo item do cardápio. |
| GET | `/api/itens-cardapio` | Lista todos os itens do cardápio. |
| GET | `/api/itens-cardapio?restauranteId={id}` | Lista itens do cardápio filtrando por restaurante. |
| GET | `/api/itens-cardapio/{id}` | Busca um item do cardápio pelo ID. |
| PUT | `/api/itens-cardapio/{id}` | Atualiza um item do cardápio existente. |
| DELETE | `/api/itens-cardapio/{id}` | Remove um item do cardápio pelo ID. |

#### Exemplo de payload para cadastro de item do cardápio

```json
{
  "nome": "Prato Executivo",
  "descricao": "Arroz, feijão, bife, batata frita e salada",
  "preco": 29.90,
  "disponivelLocal": true,
  "caminhoFoto": "/uploads/cardapio/prato-executivo.jpg",
  "restauranteId": 1
}
```

#### Exemplo de payload para atualização de item do cardápio

```json
{
  "nome": "Prato Executivo Atualizado",
  "descricao": "Arroz, feijão, frango grelhado, batata frita e salada",
  "preco": 34.90,
  "disponivelLocal": false,
  "caminhoFoto": "/uploads/cardapio/prato-executivo-atualizado.jpg"
}
```

## Documentação Swagger/OpenAPI

Após iniciar a aplicação, a documentação da API pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

Também é possível acessar a especificação OpenAPI em formato JSON:

```text
http://localhost:8080/v3/api-docs
```

No Swagger estão documentados os seguintes grupos de endpoints:

- Usuários
- Endereços
- Restaurantes
- Tipos de Usuário
- Health Check
- Itens do Cardápio

## Collections do Postman

O projeto possui arquivos exportados do Postman na pasta `collections`:

```text
collections/FIAP-Tech-Challenge.postman_collection.json
collections/FIAP-Tech-Challenge.local.postman_environment.json
```

### Como importar no Postman

1. Abra o Postman.
2. Clique em `Import`.
3. Importe o arquivo `FIAP-Tech-Challenge.postman_collection.json`.
4. Importe o arquivo `FIAP-Tech-Challenge.local.postman_environment.json`.
5. Selecione o environment `FIAP Tech Challenge - Local`.
6. Execute as requisições com a aplicação rodando em `http://localhost:8080`.

O environment local possui a variável:

```text
baseUrl = http://localhost:8080
```

Além disso, possui variáveis auxiliares para IDs usados nos testes:

```text
tipoUsuarioId
enderecoId
userId
restauranteId
itemCardapioId
```

## Atualização da Collection pelo OpenAPI

Opcionalmente, a Collection do Postman pode ser atualizada com base no contrato OpenAPI usando o script:

```text
update-postman-collection.ps1
```

Esse script foi criado para ambiente PowerShell e pode ser utilizado para:

- Baixar o contrato OpenAPI em `target/openapi.json`.
- Gerar a Collection com base no contrato.
- Atualizar `collections/FIAP-Tech-Challenge.postman_collection.json`.
- Criar backup automático da Collection anterior.
- Validar o fluxo com a opção `-DryRun`.

Exemplo de uso no PowerShell:

```powershell
.\update-postman-collection.ps1
```

Para validar sem alterar arquivos:

```powershell
.\update-postman-collection.ps1 -DryRun
```

## Configuração do banco de dados

O projeto utiliza **PostgreSQL** como banco de dados.

As configurações principais estão em:

```text
src/main/resources/application.yml
```

A aplicação utiliza as seguintes variáveis de ambiente, com valores padrão:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/tech_challenge
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=admin
```

Ao rodar com Docker Compose, o banco é iniciado automaticamente conforme as configurações do arquivo `docker-compose.yml`.

O script de criação inicial do banco está em:

```text
docker/db/init.sql
```

## Rodando localmente

### Pré-requisitos

- Java 21 instalado.
- Maven instalado.
- Docker Desktop instalado e em execução.
- PostgreSQL disponível localmente ou via Docker Compose.

### Compilar o projeto

```bash
mvn clean install
```

### Iniciar a aplicação localmente

```bash
mvn spring-boot:run
```

Também é possível iniciar a aplicação diretamente pela IDE executando a classe principal:

```text
TechChallengeApplication
```

Após iniciar, a aplicação ficará disponível em:

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

## Testes automatizados

O projeto possui testes automatizados organizados em `src/test`, incluindo testes para:

- Services.
- Repositories.
- Adapters.
- Controllers.
- Teste de integração.

Para executar os testes:

```bash
mvn test
```

Para executar o build completo com testes:

```bash
mvn clean install
```

Caso algum teste de integração dependa do banco de dados, mantenha o ambiente Docker ativo antes de executar os testes.

## Validação do projeto

O projeto foi validado com:

```bash
mvn clean install
```

Também foram testados:

- Inicialização da aplicação na porta `8080`.
- Acesso ao Swagger.
- CRUD de tipos de usuário.
- CRUD de usuários.
- Associação de usuário com tipo de usuário.
- CRUD de endereços.
- CRUD de restaurantes.
- CRUD de itens do cardápio.
- Cadastro de item do cardápio com `disponivelLocal` e `caminhoFoto`.
- Busca de itens do cardápio por restaurante.
- Collections do Postman disponíveis na pasta `collections`.

## Entregáveis contemplados

Este projeto contempla os principais pontos solicitados para a Fase 2:

- Funcionalidades de cadastro de tipo de usuário, restaurante e itens do cardápio.
- Endpoints REST funcionando.
- Código organizado em camadas.
- Documentação da API com Swagger/OpenAPI.
- Documentação do projeto no README.
- Collections do Postman para teste da API.
- Configuração Docker Compose.
- Repositório de código versionado.
- Estrutura baseada em Clean Architecture.
- Testes automatizados.