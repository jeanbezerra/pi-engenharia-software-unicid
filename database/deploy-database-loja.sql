CREATE TABLE categorias (
    categoria_id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE produtos (
    produto_id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    estoque INT NOT NULL,
    categoria_id INT,
    imagem_url VARCHAR(255),
    FOREIGN KEY (categoria_id) REFERENCES categorias(categoria_id)
);

CREATE TABLE clientes (
    cliente_id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    endereco TEXT,
    telefone VARCHAR(50)
);

CREATE TABLE pedidos (
    pedido_id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id)
);

CREATE TABLE itens_pedido (
    item_id SERIAL PRIMARY KEY,
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(pedido_id),
    FOREIGN KEY (produto_id) REFERENCES produtos(produto_id)
);

CREATE TABLE pagamentos (
    pagamento_id SERIAL PRIMARY KEY,
    pedido_id INT NOT NULL,
    tipo_pagamento VARCHAR(50) NOT NULL,
    status_pagamento VARCHAR(50) NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(pedido_id)
);

-- Inserts para simular dados

-- Inserindo categorias
INSERT INTO categorias (nome) VALUES ('Cupcakes Clássicos');
INSERT INTO categorias (nome) VALUES ('Cupcakes Gourmet');
INSERT INTO categorias (nome) VALUES ('Cupcakes Veganos');

-- Inserindo produtos
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Chocolate', 'Cupcake clássico de chocolate com cobertura de ganache', 10.00, 100, 1, 'url_cupcake_chocolate.jpg');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Red Velvet', 'Cupcake gourmet de red velvet com cream cheese', 12.50, 50, 2, 'url_cupcake_red_velvet.jpg');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Vegano de Baunilha', 'Cupcake vegano de baunilha com cobertura de creme de coco', 11.00, 30, 3, 'url_cupcake_vegano.jpg');

-- Inserindo clientes
INSERT INTO clientes (nome, email, senha, endereco, telefone) VALUES ('João Silva', 'joao.silva@example.com', 'senha123', 'Rua das Flores, 123', '11999999999');
INSERT INTO clientes (nome, email, senha, endereco, telefone) VALUES ('Maria Oliveira', 'maria.oliveira@example.com', 'senha456', 'Av. Paulista, 1000', '11888888888');

-- Inserindo pedidos
INSERT INTO pedidos (cliente_id, data_pedido, status) VALUES (1, CURRENT_TIMESTAMP, 'Pendente');
INSERT INTO pedidos (cliente_id, data_pedido, status) VALUES (2, CURRENT_TIMESTAMP, 'Enviado');

-- Inserindo itens do pedido
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES (1, 1, 2, 10.00);
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES (1, 3, 1, 11.00);
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES (2, 2, 3, 12.50);

-- Inserindo pagamentos
INSERT INTO pagamentos (pedido_id, tipo_pagamento, status_pagamento, data_pagamento) VALUES (1, 'Cartão de Crédito', 'Aprovado', CURRENT_TIMESTAMP);
INSERT INTO pagamentos (pedido_id, tipo_pagamento, status_pagamento, data_pagamento) VALUES (2, 'Boleto', 'Pendente', CURRENT_TIMESTAMP);