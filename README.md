# Sistema de Gerenciamento de Professores

## 1. Sobre o projeto

Este projeto consiste em uma API REST desenvolvida com Java e Spring Boot para o gerenciamento de professores e departamentos. A aplicação permite cadastrar, consultar, atualizar e excluir registros, utilizando PostgreSQL para persistência dos dados.

Além da API, foi desenvolvida uma interface web para facilitar a interação do usuário com o sistema, permitindo realizar as principais operações por meio de telas, sem precisar enviar requisições manualmente por ferramentas de teste.

## 2. Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Insomnia para testes das requisições HTTP
- HTML5
- CSS3
- JavaScript

## 3. Funcionalidades

### Professores

- Cadastrar professores.
- Listar todos os professores.
- Consultar professor por ID.
- Buscar professores pelo nome.
- Buscar professores pela área de atuação.
- Consultar professores por departamento.
- Atualizar os dados de um professor.
- Excluir professores.
- Validar e-mails duplicados.
- Exigir um departamento válido para o cadastro e a atualização.

### Departamentos

- Cadastrar departamentos.
- Listar todos os departamentos.
- Consultar departamento por ID.
- Atualizar departamentos.
- Excluir departamentos.

### Tratamento de erros

- Respostas padronizadas para erros da API.
- Mensagens para registros não encontrados.
- Validação de e-mails duplicados.
- Tratamento de JSON inválido.
- Validação de dados obrigatórios.

### Interface web

Foi desenvolvida uma interface web para tornar o sistema mais prático e intuitivo, facilitando o acesso às funcionalidades da API.

A interface permite:

- Cadastrar professores por meio de um formulário.
- Visualizar os professores cadastrados em uma tabela.
- Pesquisar professores por nome ou área de atuação.
- Selecionar o departamento do professor.
- Editar os dados de um professor.
- Excluir professores cadastrados.
- Atualizar a listagem de registros.

A interface foi construída com HTML, CSS e JavaScript e se comunica com a API REST desenvolvida em Spring Boot.

## 4. Relacionamento entre as entidades

Um departamento pode estar associado a vários professores, enquanto cada professor está associado a um departamento.

Esse relacionamento é representado como **muitos para um (Many-to-One)** na entidade Professor.

## 5. Organização do projeto

O código está organizado em camadas:

- **Model:** representa as entidades Professor e Departamento.
- **Repository:** realiza o acesso aos dados por meio do Spring Data JPA.
- **Service:** concentra as regras de negócio e as validações.
- **Controller:** disponibiliza os endpoints da API REST.
- **Exception:** centraliza o tratamento de erros.
- **Static:** contém os arquivos da interface web, como HTML, CSS e JavaScript, caso estejam localizados em `src/main/resources/static`.

## 6. Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- JDK 21.
- PostgreSQL.
- Uma IDE, como Visual Studio Code ou IntelliJ IDEA.

## 7. Configuração do banco de dados

Crie um banco de dados PostgreSQL chamado `sistema_professores`.

Configure as credenciais e a URL de conexão no arquivo:

`src/main/resources/application.properties`

A aplicação utiliza o Hibernate para atualizar a estrutura das tabelas conforme a configuração do projeto.


## 8. Como executar o projeto

Abra o terminal na pasta raiz do projeto.

Execute o comando para limpar e compilar a aplicação:

```bash
.\mvnw.cmd clean
```

Em seguida, inicie a aplicação:

```bash
.\mvnw.cmd spring-boot:run
```

Por padrão, a API estará disponível em:

http://localhost:8080

Se a interface estiver localizada em `src/main/resources/static`, ela poderá ser acessada pelo endereço configurado para o arquivo inicial, normalmente:

http://localhost:8080/

## 9. Documentação da API — Swagger

Com a aplicação em execução, acesse:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Documentação OpenAPI em JSON:** http://localhost:8080/v3/api-docs

O Swagger permite consultar os endpoints, visualizar suas descrições e executar requisições HTTP para testar a API.

## 10. Principais endpoints

### Professores

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/professores` | Lista os professores |
| GET | `/professor/{id}` | Busca professor por ID |
| GET | `/professores/nome/{nome}` | Busca por nome |
| GET | `/professores/area/{area}` | Busca por área |
| GET | `/professores/departamento/{departamentoId}` | Busca por departamento |
| POST | `/professores` | Cadastra professor |
| PUT | `/professor/{id}` | Atualiza professor |
| DELETE | `/professor/{id}` | Exclui professor |

### Departamentos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/departamentos` | Lista os departamentos |
| GET | `/departamentos/{id}` | Busca departamento por ID |
| POST | `/departamentos` | Cadastra departamento |
| PUT | `/departamentos/{id}` | Atualiza departamento |
| DELETE | `/departamentos/{id}` | Exclui departamento |

## 11. Testes

Os endpoints podem ser testados pelo Swagger UI ou pelo Insomnia, verificando as operações de cadastro, consulta, atualização e exclusão, além das respostas para solicitações inválidas.

A interface web também pode ser utilizada para testar as principais operações de gerenciamento de professores, desde que a aplicação e o banco de dados estejam configurados e em execução.

## 12. Vídeo de demonstração

O vídeo apresenta a execução do sistema e demonstra suas principais funcionalidades, incluindo a interface web, o cadastro e a consulta de professores, a edição e a exclusão de registros e a documentação da API.

**Link do vídeo:** [https://youtu.be/2HLbfHMh92g]

## 13. Autor

**Marcus Mikael Rodrigues Vieira**
