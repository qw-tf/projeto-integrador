-- 1. Cria usuário admin se não existir
DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'admin') THEN
      CREATE ROLE admin WITH LOGIN PASSWORD '0109';
   END IF;
END
$$;

-- 2. Cria banco comercio com owner admin se não existir
DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'comercio') THEN
      CREATE DATABASE comercio OWNER admin;
   END IF;
END
$$;

-- 3. Conecta no banco comercio
\c comercio;

-- 4. Cria extensão dblink se necessário
CREATE EXTENSION IF NOT EXISTS dblink;

-- 5. Criação das tabelas (com IF NOT EXISTS)
CREATE TABLE IF NOT EXISTS produtos (
    codigo INT PRIMARY KEY,
    nome TEXT NOT NULL,
    valorVenda NUMERIC(10,2) NOT NULL,
    valorCompra NUMERIC(10,2) NOT NULL,
    quantidade INT,
    dataDeValidade DATE,
    quantidadeTotal INT
);

CREATE TABLE IF NOT EXISTS gastos (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    data DATE NOT NULL,
    pessoal BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS vendas (
    id INT PRIMARY KEY,
    data DATE NOT NULL DEFAULT CURRENT_DATE,
    descricao TEXT NOT NULL,
    quantidade INTEGER NOT NULL,
    ganhoBruto NUMERIC(10,2), 
    formaPagamento VARCHAR(50) NOT NULL,
    valorTotal NUMERIC(10, 2) NOT NULL,
    gasto NUMERIC(10,2) DEFAULT 0.00
);

CREATE TABLE IF NOT EXISTS fiados (
    id INT PRIMARY KEY,
    idVenda INT NOT NULL REFERENCES vendas(id) ON DELETE CASCADE,
    nomeCliente VARCHAR(100) NOT NULL,
    valorRestante NUMERIC(10, 2) NOT NULL,
    dataCriacao DATE NOT NULL,
    dataQuitado DATE
);

-- 6. Cria função para remover produto se quantidade <= 0
CREATE OR REPLACE FUNCTION remover_produto_se_zero()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.quantidade <= 0 THEN
        DELETE FROM produtos WHERE codigo = NEW.codigo;
        RETURN NULL;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- 7. Cria trigger para chamar a função acima
DROP TRIGGER IF EXISTS trigger_remover_produto ON produtos;

CREATE TRIGGER trigger_remover_produto
BEFORE UPDATE ON produtos
FOR EACH ROW
WHEN (NEW.quantidade <= 0)
EXECUTE FUNCTION remover_produto_se_zero();

-- 8. Dá todas as permissões pro admin no banco comercio
GRANT ALL PRIVILEGES ON DATABASE comercio TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL TRIGGERS IN SCHEMA public TO admin;