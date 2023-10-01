CREATE TABLE usuario (
    id INT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    senhasimetrica VARCHAR(512),
    senhaasimetrica VARCHAR(512),
    publickey TEXT,
    privatekey TEXT
);
