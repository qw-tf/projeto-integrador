ALTER USER  admin PASSWORD '0109';


CREATE TABLE produtos (
    codigo INT PRIMARY KEY,
    nome TEXT NOT NULL,
    valorVenda NUMERIC(10,2) NOT NULL,
    valorCompra NUMERIC(10,2) NOT NULL,
    quantidade INT,
    dataDeValidade DATE


);


CREATE TABLE gastos (
    id INTEGER PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    data DATE NOT NULL,
    pessoal BOOLEAN NOT NULL,
    conta BOOLEAN NOT NULL,
    pago BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE vendas (
    id INT PRIMARY KEY,
    data DATE NOT NULL DEFAULT CURRENT_DATE,
    descricao TEXT NOT NULL,
    quantidade INTEGER NOT NULL,
    ganhoBruto NUMERIC(10,2), 
    formaPagamento VARCHAR(50) NOT NULL,
    valorTotal NUMERIC(10, 2) NOT NULL
);

CREATE TABLE fiados (
    id INT PRIMARY KEY,
    idVenda INT NOT NULL REFERENCES vendas(id) ON DELETE CASCADE,
    nomeCliente VARCHAR(100) NOT NULL,
    valorRestante NUMERIC(10, 2) NOT NULL,
    dataCriacao DATE NOT NULL,
    dataQuitado DATE
);