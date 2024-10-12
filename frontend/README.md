# Estrutura de Páginas

## 1. Página Inicial (Home)
- **URL:** `/home`
- **Componentes:**
  - Banner promocional
  - Destaques dos cuppycakes (produtos em destaque)
  - Botão para ver mais cuppycakes ou realizar pedidos

## 2. Catálogo de Produtos (Produtos)
- **URL:** `/produtos`
- **Componentes:**
  - Filtros de pesquisa (sabor, preço, categoria)
  - Lista de cuppycakes em formato de grid
  - Paginação

## 3. Detalhes do Produto
- **URL:** `/produto/:id`
- **Componentes:**
  - Imagem grande do cuppycake
  - Descrição detalhada (ingredientes, valor nutricional)
  - Opções de personalização (cobertura, tamanho)
  - Botão "Adicionar ao carrinho"

## 4. Carrinho de Compras
- **URL:** `/carrinho`
- **Componentes:**
  - Lista de produtos adicionados ao carrinho (nome, quantidade, preço)
  - Botões para alterar quantidade e remover produtos
  - Resumo do pedido (valor total)
  - Botão para "Continuar comprando" ou "Finalizar pedido"

## 5. Checkout
- **URL:** `/checkout`
- **Componentes:**
  - Formulário para informações do cliente (nome, endereço, contato)
  - Escolha do método de pagamento (cartão, PIX, boleto)
  - Revisão do pedido
  - Botão para "Finalizar compra"

## 6. Login/Registro
- **URL:** `/login` e `/registro`
- **Componentes:**
  - Formulário de login (usuário e senha)
  - Formulário de registro (nome, e-mail, senha)
  - Link para recuperação de senha

## 7. Perfil do Usuário
- **URL:** `/perfil`
- **Componentes:**
  - Informações do usuário (nome, e-mail, histórico de pedidos)
  - Opções para editar informações ou senha

## 8. Confirmação do Pedido
- **URL:** `/confirmacao/:id`
- **Componentes:**
  - Resumo do pedido concluído
  - Mensagem de agradecimento
  - Estimativa de entrega
  - Botões para voltar à home ou visualizar mais produtos

# Componentes Globais

## Navbar (Barra de Navegação)
- Links: Home, Produtos, Carrinho, Perfil
- Indicador de quantidade de itens no carrinho

## Footer
- Informações de contato
- Links para redes sociais, termos de serviço, política de privacidade

# Serviços

## ProductService
- Buscar lista de cuppycakes
- Obter detalhes do produto

## CartService
- Adicionar/remover produtos do carrinho
- Calcular total do pedido

## OrderService
- Processar pedidos e enviar dados para a API

## AuthService
- Autenticação e registro de usuários

# Módulos

## ProductModule
- Gerencia listagem e detalhes dos cuppycakes

## CartModule
- Gerencia carrinho de compras e fluxo de checkout

## UserModule
- Gerencia autenticação, login e perfil do usuário
