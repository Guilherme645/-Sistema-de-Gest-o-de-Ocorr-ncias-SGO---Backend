CREATE TABLE ocorrencias (
    id UUID PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    status VARCHAR(50) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP
);