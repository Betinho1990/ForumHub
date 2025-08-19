# ForumHub

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![JWT](https://img.shields.io/badge/JWT-Authentication-orange)

ForumHub é uma aplicação de fórum desenvolvida com **Spring Boot**, **MySQL** e **JWT** para autenticação e controle de acesso.  
O projeto permite o gerenciamento de tópicos, incluindo criação, leitura, atualização e exclusão, com autenticação segura via tokens JWT.

---

## Funcionalidades

- Autenticação via JWT (`/login`).
- CRUD de tópicos do fórum (apenas usuários autenticados).
- Controle de acesso seguro usando tokens JWT.
- Criação automática de usuário administrador inicial.
- Conexão com banco MySQL configurável via variáveis de ambiente.
- Logs das operações no console.

---

## Tecnologias utilizadas

- Java 17
- Spring Boot 3.5.4
- Spring Security
- JPA / Hibernate
- MySQL 8
- JWT (JSON Web Token)
- Flyway (migrações de banco de dados)

---

## Pré-requisitos

- Java 17 ou superior
- Maven
- MySQL 8 ou superior
- Variáveis de ambiente configuradas (ex.: `JWT_SECRET`, `JWT_EXPIRATION`, `APP_ADMIN_LOGIN`, `APP_ADMIN_PASSWORD`, `SERVER_PORT`)

---

## Configuração

Crie as variáveis de ambiente necessárias:

```bash
# JWT secret (substitua pelo seu segredo forte e longo)
export JWT_SECRET="sua_chave_super_secreta_aqui"

# Expiração do token em milissegundos
export JWT_EXPIRATION=86400000

# Admin inicial
export APP_ADMIN_LOGIN=admin
export APP_ADMIN_PASSWORD=sua_senha_segura_aqui

# Porta da aplicação
export SERVER_PORT=8080

# Banco de dados MySQL
export DB_URL=jdbc:mysql://localhost:3306/forumhub_db
export DB_USERNAME=seu_usuario
export DB_PASSWORD=sua_senha

```
No Windows PowerShell, utilize setx ao invés de export

## Executando o projeto

1. Clone o repositório:

```bash

git clone https://github.com/Betinho1990/forumhub.git
cd forumhub

```
2. Build e execute com Maven:

```bash

mvn clean install
mvn spring-boot:run

```
3. Acesse a aplicação:

---

- URL base: http://localhost:8080

- Endpoint de login: POST /login com JSON:

---

```bash

json

{
  "login": "SEU_LOGIN_AQUI",
  "senha": "SUA_SENHA_AQUI"
}

```

Use o token retornado para autenticar as demais requisições com header Authorization: Bearer <TOKEN>.

## Endpoints principais

| Método | URL           | Descrição                       |
| ------ | ------------- | ------------------------------- |
| POST   | /login        | Autenticação e geração de token |
| GET    | /topicos      | Listar tópicos (autenticado)    |
| POST   | /topicos      | Criar tópico (autenticado)      |
| PUT    | /topicos/{id} | Atualizar tópico (autenticado)  |
| DELETE | /topicos/{id} | Remover tópico (autenticado)    |

## Estrutura do projeto

src
 └─ main
    ├─ java
    │   └─ com.Forumhub.ApiRest
    │       ├─ model
    │       ├─ repository
    │       ├─ service
    │       ├─ security
    │       └─ controller
    └─ resources
        ├─ application.properties
        └─ db/migration (Flyway)

## Observações

---

- Senhas nunca são armazenadas em texto puro; são criptografadas com BCrypt.

- Tokens JWT devem ser incluídos no header Authorization em todas as requisições autenticadas.

- Variáveis de ambiente permitem alterar facilmente senha, porta, JWT e conexão com banco sem modificar o código.

---

## Licença

MIT License

** Desenvolvido por: Roberto Filho
