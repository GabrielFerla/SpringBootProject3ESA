# 🎓 Sistema de Gerenciamento - FIAP 3ESA

## 👥 Integrantes do Grupo

- **Gabriel Ferla** - RM550695
- **Henri de Oliveira Lopes** - RM98347
- **Lorenzo Gomes Andreata** - RM551117
- **Lucas Moreno Matheus** - RM97158
- **Victor Flávio Demarchi Viana** - RM99389

---

## 📋 Sobre o Projeto

API REST desenvolvida em Spring Boot para gerenciamento de Instrutores, Alunos e Usuários com sistema completo de autenticação e autorização usando JWT (JSON Web Token).

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Security** - Autenticação e autorização
- **JWT (JSON Web Token)** - Gerenciamento de tokens
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados
- **Flyway** - Migrations do banco de dados
- **Bean Validation** - Validação de dados
- **Lombok** - Redução de código boilerplate
- **SpringDoc OpenAPI (Swagger)** - Documentação da API

---

## 📚 Funcionalidades

### ✅ Implementado

#### 🔐 Autenticação
- Login com JWT
- Token de autenticação com expiração configurável
- Senhas encriptadas com BCrypt

#### 👥 Gerenciamento de Usuários (ADMIN apenas)
- ✅ Cadastrar novos usuários
- ✅ Listar todos os usuários (paginado)
- ✅ Detalhar usuário específico
- ✅ Atualizar perfil do usuário (ADMIN/USER)
- ✅ Excluir usuário
- ✅ Usuário pode alterar sua própria senha

#### 👨‍🏫 Gerenciamento de Instrutores
- ✅ Cadastrar instrutor
- ✅ Listar instrutores ativos (paginado)
- ✅ Atualizar dados do instrutor
- ✅ Excluir instrutor (soft delete)
- ✅ Detalhar instrutor específico

#### 👨‍🎓 Gerenciamento de Alunos
- ✅ Cadastrar aluno
- ✅ Listar alunos ativos (paginado)
- ✅ Atualizar dados do aluno
- ✅ Excluir aluno (soft delete)
- ✅ Detalhar aluno específico

#### 📖 Documentação
- ✅ Swagger UI integrado
- ✅ API Docs em formato OpenAPI 3.0

---

## 🔧 Configuração do Ambiente

### Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- MySQL 8.0+

### 1️⃣ Configurar o Banco de Dados

Crie um banco de dados MySQL:

```sql
CREATE DATABASE projeto3esa;
```

### 2️⃣ Configurar application.properties

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost/projeto3esa
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3️⃣ Instalar Dependências

```bash
mvn clean install
```

### 4️⃣ Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 📖 Acessando a Documentação (Swagger)

Após iniciar a aplicação, acesse:

### Swagger UI (Interface Interativa)
```
http://localhost:8080/swagger-ui.html
```

### API Docs (JSON)
```
http://localhost:8080/v3/api-docs
```

---

## 🔐 Autenticação e Autorização

### Perfis de Usuário

O sistema possui dois perfis:

| Perfil | Permissões |
|--------|-----------|
| **ADMIN** | Acesso total ao sistema, incluindo gerenciamento de usuários |
| **USER** | Acesso aos endpoints de Alunos e Instrutores, pode alterar sua própria senha |

### Usuário Padrão para Testes

Um usuário administrador é criado automaticamente:

```
Login: admin
Senha: admin123
Perfil: ADMIN
```

---

## 🧪 Como Testar a API

### Opção 1: Usando o Swagger UI (Recomendado)

1. **Acesse o Swagger**: `http://localhost:8080/swagger-ui.html`

2. **Faça Login**:
   - Vá até a seção **"Autenticação"**
   - Execute `POST /login`
   - Use as credenciais:
     ```json
     {
       "login": "admin",
       "senha": "admin123"
     }
     ```
   - Copie o token JWT retornado

3. **Autorize no Swagger**:
   - Clique no botão **🔒 "Authorize"** (canto superior direito)
   - Cole o token JWT (apenas o token, sem "Bearer")
   - Clique em **"Authorize"** e depois **"Close"**

4. **Teste os Endpoints**:
   - Agora você pode testar todos os endpoints!
   - O token será incluído automaticamente nas requisições

### Opção 2: Usando cURL ou Postman

#### 1. Fazer Login

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "admin",
    "senha": "admin123"
  }'
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 2. Usar o Token nas Requisições

Inclua o token no header `Authorization`:

```bash
curl -X GET http://localhost:8080/usuarios \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

---

## 📋 Endpoints da API

### 🔐 Autenticação (Público)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/login` | Realizar login e obter token JWT |

### 👥 Usuários (ADMIN apenas)

| Método | Endpoint | Descrição | Permissão |
|--------|----------|-----------|-----------|
| `POST` | `/usuarios` | Cadastrar novo usuário | ADMIN |
| `GET` | `/usuarios` | Listar usuários (paginado) | ADMIN |
| `GET` | `/usuarios/{id}` | Detalhar usuário | ADMIN |
| `PUT` | `/usuarios/{id}` | Atualizar perfil do usuário | ADMIN |
| `DELETE` | `/usuarios/{id}` | Excluir usuário | ADMIN |
| `PUT` | `/usuarios/alterar-senha` | Alterar própria senha | Autenticado |

### 👨‍🏫 Instrutores (Autenticado)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/instrutor` | Cadastrar instrutor |
| `GET` | `/instrutor` | Listar instrutores ativos (paginado) |
| `GET` | `/instrutor/{id}` | Detalhar instrutor |
| `PUT` | `/instrutor` | Atualizar instrutor |
| `DELETE` | `/instrutor?id={id}` | Excluir instrutor (soft delete) |

### 👨‍🎓 Alunos (Autenticado)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/alunos` | Cadastrar aluno |
| `GET` | `/alunos` | Listar alunos ativos (paginado) |
| `GET` | `/alunos/{id}` | Detalhar aluno |
| `PUT` | `/alunos` | Atualizar aluno |
| `DELETE` | `/alunos/{id}` | Excluir aluno (soft delete) |

---

## 📝 Exemplos de Requisições

### Cadastrar Novo Usuário (ADMIN)

```json
POST /usuarios
Authorization: Bearer {token}

{
  "login": "usuario1",
  "senha": "senha123",
  "perfil": "USER"
}
```

### Cadastrar Instrutor

```json
POST /instrutor
Authorization: Bearer {token}

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "especialidade": "NATACAO",
  "endereco": {
    "logradouro": "Rua das Flores",
    "bairro": "Centro",
    "cep": "01234567",
    "cidade": "São Paulo",
    "uf": "SP",
    "numero": "100",
    "complemento": "Apto 5"
  }
}
```

### Cadastrar Aluno

```json
POST /alunos
Authorization: Bearer {token}

{
  "nome": "Maria Santos",
  "email": "maria@email.com",
  "cpf": "12345678901",
  "telefone": "11988888888",
  "endereco": {
    "logradouro": "Av. Paulista",
    "bairro": "Bela Vista",
    "cep": "01310000",
    "cidade": "São Paulo",
    "uf": "SP",
    "numero": "1000",
    "complemento": ""
  }
}
```

### Alterar Própria Senha

```json
PUT /usuarios/alterar-senha
Authorization: Bearer {token}

{
  "senhaAtual": "senha123",
  "novaSenha": "novaSenha456"
}
```

---

## 🗃️ Estrutura do Banco de Dados

### Tabela: usuarios

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | Chave primária (auto incremento) |
| login | VARCHAR(100) | Login único do usuário |
| senha | VARCHAR(255) | Senha encriptada (BCrypt) |
| perfil | VARCHAR(20) | ADMIN ou USER |

### Tabela: instrutores

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | Chave primária |
| nome | VARCHAR(100) | Nome do instrutor |
| email | VARCHAR(100) | Email |
| telefone | VARCHAR(20) | Telefone |
| especialidade | VARCHAR(100) | Especialidade |
| ativo | BOOLEAN | Status (soft delete) |
| ... | ... | Campos de endereço |

### Tabela: alunos

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | Chave primária |
| nome | VARCHAR(100) | Nome do aluno |
| email | VARCHAR(100) | Email |
| cpf | VARCHAR(11) | CPF |
| telefone | VARCHAR(20) | Telefone |
| ativo | BOOLEAN | Status (soft delete) |
| ... | ... | Campos de endereço |

---

## 🔒 Segurança

### Implementações de Segurança

✅ **Senhas Encriptadas**: Todas as senhas são armazenadas usando BCrypt
✅ **JWT Token**: Autenticação stateless com tokens JWT
✅ **Autorização por Perfil**: Controle de acesso baseado em roles (ADMIN/USER)
✅ **CSRF Desabilitado**: Apropriado para API REST stateless
✅ **Validação de Dados**: Bean Validation em todos os DTOs

### Configuração do Token JWT

Edite no `application.properties`:

```properties
api.security.token.secret=${JWT_SECRET:12345678}
```

⚠️ **Importante**: Em produção, use uma variável de ambiente para o secret:

```bash
export JWT_SECRET=seu_secret_super_seguro_aqui
```

---

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/fiap3esa/spring_boot_project/
│   │   ├── controller/           # Controllers REST
│   │   │   ├── AlunoController.java
│   │   │   ├── InstrutorController.java
│   │   │   ├── UsuarioController.java
│   │   │   └── AutenticacaoController.java
│   │   │
│   │   ├── domain/               # Entidades e DTOs
│   │   │   ├── aluno/
│   │   │   ├── instrutor/
│   │   │   ├── usuario/
│   │   │   └── endereco/
│   │   │
│   │   ├── infra/                # Infraestrutura
│   │   │   ├── security/         # Configurações de segurança
│   │   │   ├── exception/        # Tratamento de exceções
│   │   │   └── springdoc/        # Configuração do Swagger
│   │   │
│   │   └── SpringBootProject3EsaApplication.java
│   │
│   └── resources/
│       ├── application.properties
│       └── db/migration/         # Scripts Flyway
│
└── test/                         # Testes
```

---

## 🛠️ Migrations do Banco de Dados

As migrations são executadas automaticamente pelo Flyway ao iniciar a aplicação:

1. `V1` - Criação da tabela instrutores
2. `V2` - Adição de telefone em instrutores
3. `V3` - Adição de campo ativo em instrutores
4. `V4` - Criação da tabela usuarios
5. `V5` - Criação da tabela alunos
6. `V6` - Adição de campo ativo em alunos
7. `V7` - Adição de campo perfil em usuarios
8. `V8` - Inserção do usuário admin padrão

---

## 🧪 Testando com Dados de Exemplo

### 1. Fazer Login

Use o usuário padrão: `admin` / `admin123`

### 2. Criar um Usuário Regular

```json
POST /usuarios
{
  "login": "funcionario",
  "senha": "func123",
  "perfil": "USER"
}
```

### 3. Cadastrar um Instrutor

```json
POST /instrutor
{
  "nome": "Carlos Instrutor",
  "email": "carlos@email.com",
  "telefone": "11977777777",
  "especialidade": "MUSCULACAO",
  "endereco": {
    "logradouro": "Rua Fitness",
    "bairro": "Centro",
    "cep": "12345678",
    "cidade": "São Paulo",
    "uf": "SP",
    "numero": "50"
  }
}
```

### 4. Cadastrar um Aluno

```json
POST /alunos
{
  "nome": "Ana Aluna",
  "email": "ana@email.com",
  "cpf": "98765432100",
  "telefone": "11966666666",
  "endereco": {
    "logradouro": "Av. Academia",
    "bairro": "Jardim",
    "cep": "87654321",
    "cidade": "São Paulo",
    "uf": "SP",
    "numero": "200"
  }
}
```

---

## ❗ Tratamento de Erros

A API retorna respostas padronizadas para erros:

### Erro de Validação (400 Bad Request)

```json
{
  "campo": "email",
  "mensagem": "Email é obrigatório"
}
```

### Erro de Autenticação (401 Unauthorized)

```json
{
  "message": "Token JWT inválido ou expirado"
}
```

### Erro de Autorização (403 Forbidden)

```json
{
  "message": "Acesso negado"
}
```

### Erro de Recurso Não Encontrado (404 Not Found)

```json
{
  "message": "Recurso não encontrado"
}
```

---

## 🐛 Troubleshooting (Solução de Problemas)

### Erro 403 no Login

**Problema**: Recebendo erro 403 ao tentar fazer login.

**Solução**: 
- Certifique-se de que está usando o método `POST` (não GET)
- Verifique se o `Content-Type` é `application/json`
- Reinicie a aplicação após as alterações no código

### Erro 401 Unauthorized

**Problema**: Token JWT inválido ou expirado.

**Solução**:
- Faça login novamente para obter um novo token
- Verifique se o token está sendo enviado corretamente no header `Authorization: Bearer {token}`
- Confirme que não há espaços extras no token

### Erro de Conexão com Banco de Dados

**Problema**: `Communications link failure` ou `Access denied`.

**Solução**:
1. Verifique se o MySQL está rodando
2. Confirme as credenciais no `application.properties`
3. Certifique-se de que o banco `projeto3esa` foi criado

### Migration Flyway Falhou

**Problema**: Erro ao executar migrations.

**Solução**:
- Limpe o banco e recrie: `DROP DATABASE projeto3esa; CREATE DATABASE projeto3esa;`
- Remova a tabela `flyway_schema_history` se necessário
- Reinicie a aplicação

### Swagger Não Carrega

**Problema**: Página do Swagger não abre.

**Solução**:
- Verifique se a aplicação está rodando: `http://localhost:8080`
- Tente acessar: `http://localhost:8080/swagger-ui/index.html`
- Limpe o cache do navegador

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos - FIAP 3ESA.

---

**Desenvolvido com ❤️ por estudantes da FIAP**

