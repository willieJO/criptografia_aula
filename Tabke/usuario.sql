CREATE TABLE (
    id INT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    senhasimetrica VARCHAR(512),
    senhaasimetrica VARCHAR(512),
    chave_publica BLOB,
    chave_privada BLOB
);
-- esse BLOB seria pra registrar as chaves publicas e privadas da aplicação, não estou utilizando arquivos pra salvar as chaves, então quando reinicia a aplicação perco essasa chaves