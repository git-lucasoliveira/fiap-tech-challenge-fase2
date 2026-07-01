CREATE TABLE tipo_usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    rua VARCHAR(255),
    numero VARCHAR(50),
    cidade VARCHAR(100),
    cep VARCHAR(20),
    complemento VARCHAR(255),
    estado VARCHAR(100),
    bairro VARCHAR(100)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    fk_tipo_usuario INTEGER NOT NULL,
    endereco_id INTEGER,
    data_ultima_alteracao TIMESTAMP,

    CONSTRAINT fk_users_tipo
        FOREIGN KEY (fk_tipo_usuario)
        REFERENCES tipo_usuario(id),

    CONSTRAINT fk_users_endereco
        FOREIGN KEY (endereco_id)
        REFERENCES endereco(id)
);

CREATE TABLE restaurante (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo_cozinha VARCHAR(100) NOT NULL,
    horario_funcionamento VARCHAR(255),

    dono_id INTEGER NOT NULL,
    endereco_id INTEGER,

    CONSTRAINT fk_restaurante_dono
        FOREIGN KEY (dono_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_restaurante_endereco
        FOREIGN KEY (endereco_id)
        REFERENCES endereco(id)
);

CREATE TABLE item_cardapio (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    disponivel_local BOOLEAN DEFAULT FALSE,
    caminho_foto VARCHAR(255),

    restaurante_id INTEGER NOT NULL,

    CONSTRAINT fk_item_restaurante
        FOREIGN KEY (restaurante_id)
        REFERENCES restaurante(id)
        ON DELETE CASCADE
);

CREATE TABLE avaliacao (
    id SERIAL PRIMARY KEY,
    nota INTEGER NOT NULL,
    comentario TEXT,
    cliente_id INTEGER NOT NULL,
    restaurante_id INTEGER NOT NULL,

    CONSTRAINT fk_avaliacao_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_avaliacao_restaurante
        FOREIGN KEY (restaurante_id)
        REFERENCES restaurante(id)
        ON DELETE CASCADE
);

-- INDEX`S

CREATE INDEX idx_users_tipo_usuario ON users(fk_tipo_usuario);
CREATE INDEX idx_users_endereco ON users(endereco_id);

CREATE INDEX idx_restaurante_dono ON restaurante(dono_id);
CREATE INDEX idx_restaurante_endereco ON restaurante(endereco_id);
CREATE INDEX idx_restaurante_nome ON restaurante(nome);
CREATE INDEX idx_restaurante_tipo_cozinha ON restaurante(tipo_cozinha);

CREATE INDEX idx_item_restaurante ON item_cardapio(restaurante_id);

CREATE INDEX idx_avaliacao_cliente ON avaliacao(cliente_id);
CREATE INDEX idx_avaliacao_restaurante ON avaliacao(restaurante_id);

CREATE INDEX idx_endereco_cidade ON endereco(cidade);