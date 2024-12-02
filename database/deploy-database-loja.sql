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
    password VARCHAR(255) NOT NULL,
    password_salt VARCHAR(255) NOT NULL,
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


--Meta
truncate table clientes cascade;
INSERT INTO clientes (nome, email, password,password_salt, endereco, telefone) VALUES ('João Silva', 'joao.silva@email.com', 'mGzR+aVLuPggFyrOQ3TaPuSI4eSWxpBm8zwcLlR6wTM=','4xtwkSCCnofFMsn4ngXHkA==', 'Rua das Flores, 123 - São Paulo', '(11) 98765-4321');
INSERT INTO clientes (nome, email, password,password_salt, endereco, telefone) VALUES ('Maria Oliveira', 'maria.oliveira@email.com', 'mGzR+aVLuPggFyrOQ3TaPuSI4eSWxpBm8zwcLlR6wTM=','4xtwkSCCnofFMsn4ngXHkA==', 'Avenida Brasil, 456 - Rio de Janeiro', '(21) 99999-1234');
INSERT INTO users (id, name, password, password_salt, username) VALUES ('1a2b3c4d-5678-9101-1121-314151617181', 'João Silva', 'mGzR+aVLuPggFyrOQ3TaPuSI4eSWxpBm8zwcLlR6wTM=', '4xtwkSCcnoffMsn4ngXHkA==', 'joao.silva@email.com');
INSERT INTO users (id, name, password, password_salt, username) VALUES ('2b3c4d5e-6789-1011-1213-141516171819', 'Maria Oliveira', 'mGzR+aVLuPggFyrOQ3TaPuSI4eSWxpBm8zwcLlR6wTM=', '4xtwkSCcnoffMsn4ngXHkA==', 'maria.oliveira@email.com');
INSERT INTO users (id, name, password, password_salt, username) VALUES ('d4dbc31d-8a96-45d5-96d1-97f5e4d5c495', 'Administrador', 'mGzR+aVLuPggFyrOQ3TaPuSI4eSWxpBm8zwcLlR6wTM=', '4xtwkSCcnoffMsn4ngXHkA==', 'admin');
commit;



truncate table categorias cascade;
INSERT INTO categorias (categoria_id, nome) VALUES (1, 'Cupcakes Clássicos');
INSERT INTO categorias (categoria_id, nome) VALUES (2, 'Cupcakes Recheados');
INSERT INTO categorias (categoria_id, nome) VALUES (3, 'Cupcakes de Chocolate');
INSERT INTO categorias (categoria_id, nome) VALUES (4, 'Cupcakes de Frutas');
INSERT INTO categorias (categoria_id, nome) VALUES (5, 'Cupcakes Diet');
INSERT INTO categorias (categoria_id, nome) VALUES (6, 'Cupcakes Veganos');
INSERT INTO categorias (categoria_id, nome) VALUES (7, 'Cupcakes Gourmet');
INSERT INTO categorias (categoria_id, nome) VALUES (8, 'Cupcakes Infantis');
INSERT INTO categorias (categoria_id, nome) VALUES (9, 'Cupcakes de Festa');
INSERT INTO categorias (categoria_id, nome) VALUES (10, 'Cupcakes Temáticos');
commit;


truncate table produtos cascade;
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Baunilha', 'Cupcake clássico de baunilha com cobertura de chantilly.', 12.50, 50, 1, 'https://via.placeholder.com/150?text=Cupcake+Baunilha');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Morango', 'Cupcake clássico com pedaços de morango e cobertura.', 13.50, 60, 1, 'https://via.placeholder.com/150?text=Cupcake+Morango');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Brigadeiro', 'Cupcake recheado com brigadeiro e cobertura de chocolate.', 15.00, 40, 2, 'https://via.placeholder.com/150?text=Cupcake+Brigadeiro');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Doce de Leite', 'Cupcake recheado com doce de leite e cobertura de caramelo.', 16.00, 30, 2, 'https://via.placeholder.com/150?text=Cupcake+Doce+de+Leite');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Chocolate', 'Cupcake clássico de chocolate com cobertura de ganache.', 14.00, 40, 3, 'https://via.placeholder.com/150?text=Cupcake+Chocolate');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Nutella', 'Cupcake recheado com Nutella e cobertura cremosa.', 17.50, 20, 3, 'https://via.placeholder.com/150?text=Cupcake+Nutella');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Limão', 'Cupcake de limão com cobertura de merengue.', 12.00, 50, 4, 'https://via.placeholder.com/150?text=Cupcake+Lim%C3%A3o');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Frutas Vermelhas', 'Cupcake com pedaços de frutas vermelhas e chantilly.', 13.50, 45, 4, 'https://via.placeholder.com/150?text=Cupcake+Frutas+Vermelhas');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Diet de Baunilha', 'Cupcake sem açúcar, ideal para dietas.', 14.50, 30, 5, 'https://via.placeholder.com/150?text=Cupcake+Diet+Baunilha');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Diet de Chocolate', 'Cupcake de chocolate sem açúcar com cobertura de cacau.', 15.00, 20, 5, 'https://via.placeholder.com/150?text=Cupcake+Diet+Chocolate');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Vegano de Limão', 'Cupcake vegano com sabor de limão e cobertura de coco.', 16.50, 25, 6, 'https://via.placeholder.com/150?text=Cupcake+Vegano+Lim%C3%A3o');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Vegano de Frutas Vermelhas', 'Cupcake vegano com pedaços de frutas vermelhas.', 17.00, 20, 6, 'https://via.placeholder.com/150?text=Cupcake+Vegano+Frutas');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Gourmet de Pistache', 'Cupcake gourmet com pistache e cobertura cremosa.', 18.50, 15, 7, 'https://via.placeholder.com/150?text=Cupcake+Pistache');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Gourmet de Amêndoas', 'Cupcake gourmet de amêndoas com recheio de creme.', 19.00, 10, 7, 'https://via.placeholder.com/150?text=Cupcake+Am%C3%AAndoas');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Infantil de Unicórnio', 'Cupcake decorado com tema de unicórnio.', 15.50, 40, 8, 'https://via.placeholder.com/150?text=Cupcake+Unic%C3%B3rnio');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Infantil de Arco-Íris', 'Cupcake colorido com tema infantil.', 15.00, 35, 8, 'https://via.placeholder.com/150?text=Cupcake+Arco-%C3%8Dris');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Aniversário', 'Cupcake decorado para festas de aniversário.', 16.00, 50, 9, 'https://via.placeholder.com/150?text=Cupcake+Anivers%C3%A1rio');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Casamento', 'Cupcake sofisticado para casamentos.', 20.00, 20, 9, 'https://via.placeholder.com/150?text=Cupcake+Casamento');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Temático de Halloween', 'Cupcake decorado com tema de Halloween.', 18.00, 25, 10, 'https://via.placeholder.com/150?text=Cupcake+Halloween');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Temático de Natal', 'Cupcake decorado com tema natalino.', 18.50, 30, 10, 'https://via.placeholder.com/150?text=Cupcake+Natal');
commit;



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
