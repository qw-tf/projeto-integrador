
CREATE EXTENSION IF NOT EXISTS dblink;

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

DROP TRIGGER IF EXISTS trigger_remover_produto ON produtos;

CREATE TRIGGER trigger_remover_produto
BEFORE UPDATE ON produtos
FOR EACH ROW
WHEN (NEW.quantidade <= 0)
EXECUTE FUNCTION remover_produto_se_zero();

GRANT ALL PRIVILEGES ON DATABASE comercio TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO admin;
GRANT ALL ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO admin;
GRANT ALL ON ALL FUNCTIONS IN SCHEMA public TO admin;