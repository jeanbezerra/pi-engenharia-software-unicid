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

-- Metadata
INSERT INTO users (id, name, password, password_salt, username, email) VALUES ('d4dbc31d-8a96-45d5-96d1-97f5e4d5c495', 'Administrador', 'LKL96FsBEjMBo4mfAD2YjthnT6x7jjQGof/+MZGPch4=', '++No6iyBL3kP8q7hyECs3g==', 'admin','admin');
INSERT INTO clientes (nome, email, password,password_salt, endereco, telefone) VALUES ('Maria Oliveira', 'maria.oliveira@email.com', 'LKL96FsBEjMBo4mfAD2YjthnT6x7jjQGof/+MZGPch4=','++No6iyBL3kP8q7hyECs3g==', 'Avenida Brasil, 456 - Rio de Janeiro', '(21) 99999-1234');
INSERT INTO users (id, name, password, password_salt, username, email) VALUES ('2b3c4d5e-6789-1011-1213-141516171819', 'Maria Oliveira', 'LKL96FsBEjMBo4mfAD2YjthnT6x7jjQGof/+MZGPch4=', '++No6iyBL3kP8q7hyECs3g==', 'maria.oliveira@email.com','maria.oliveira@email.com');
commit;

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

INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Baunilha', 'Cupcake clássico de baunilha com cobertura de chantilly.', 12.50, 50, 1, 'https://th.bing.com/th/id/OIP.NfdtbPfZmCLqP4JhFVNwnAHaHa?rs=1&pid=ImgDetMain');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Morango', 'Cupcake clássico com pedaços de morango e cobertura.', 13.50, 60, 1, 'https://bing.com/th?id=OSK.ca72312364df13eefc7f77dfc0bd1e0f');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Brigadeiro', 'Cupcake recheado com brigadeiro e cobertura de chocolate.', 15.00, 40, 2, 'https://th.bing.com/th/id/OIP.PdG4nlk2CUNL9PPdN5Ef-wHaLH?rs=1&pid=ImgDetMain');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Chocolate', 'Cupcake clássico de chocolate com cobertura de ganache.', 14.00, 40, 3, 'https://th.bing.com/th/id/OIP.PdG4nlk2CUNL9PPdN5Ef-wHaLH?rs=1&pid=ImgDetMain');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Nutella', 'Cupcake recheado com Nutella e cobertura cremosa.', 17.50, 20, 3, 'https://th.bing.com/th/id/R.d0a1d5007cfc88b1f5896b1211e7eea9?rik=0xh3zF7IC40NyQ&pid=ImgRaw&r=0');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Diet de Chocolate', 'Cupcake de chocolate sem açúcar com cobertura de cacau.', 15.00, 20, 5, 'https://th.bing.com/th/id/OIP.PdG4nlk2CUNL9PPdN5Ef-wHaLH?rs=1&pid=ImgDetMain');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake Vegano de Limão', 'Cupcake vegano com sabor de limão e cobertura de coco.', 16.50, 25, 6, 'https://th.bing.com/th/id/OIP.RfQMaGj5W0uz7hiOKyDxvAHaE8?rs=1&pid=ImgDetMain');
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, imagem_url) VALUES ('Cupcake de Aniversário', 'Cupcake decorado para festas de aniversário.', 16.00, 50, 9, 'https://bing.com/th?id=OSK.d415a18d1a5c9eb4c452b0fcdcf5cc81');
commit;