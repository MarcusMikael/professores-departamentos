# Sistema de Gerenciamento de Professores

## 1. Sobre o projeto

O Sistema de Gerenciamento de Professores é uma aplicação desenvolvida com Java e Spring Boot para gerenciar professores e departamentos. A aplicação disponibiliza uma API REST com persistência de dados em PostgreSQL.

O sistema permite cadastrar, consultar, atualizar e excluir registros, além de realizar pesquisas por nome, área de atuação e departamento.

Também foi desenvolvida uma interface web integrada à API, permitindo executar as principais operações por meio de formulários e tabelas.

## 2. Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Insomnia
- HTML5
- CSS3
- JavaScript

## 3. Funcionalidades

### 3.1. Professores

- Cadastrar professores vinculados a departamentos existentes.
- Listar todos os professores.
- Consultar professor por ID.
- Pesquisar professores por nome.
- Pesquisar professores por área de atuação.
- Consultar professores por departamento.
- Atualizar os dados de um professor.
- Excluir professores.
- Validar dados obrigatórios e formato do e-mail.
- Impedir o cadastro de e-mails duplicados.
- Validar o departamento informado no cadastro e na atualização.

### 3.2. Departamentos

- Cadastrar departamentos.
- Listar departamentos.
- Consultar departamento por ID.
- Atualizar departamentos.
- Excluir departamentos, respeitando as regras de relacionamento com professores.

### 3.3. Interface web

A interface foi desenvolvida com HTML, CSS e JavaScript e se comunica com a API REST.

Suas funcionalidades incluem:

- Cadastro de professores por formulário.
- Listagem em tabela.
- Pesquisa por nome ou área de atuação.
- Seleção de departamento.
- Edição e exclusão de professores.
- Atualização da listagem de registros.

### 3.4. Validações e tratamento de erros

A API utiliza DTOs para receber e retornar dados de professores, evitando expor diretamente as entidades JPA nos endpoints correspondentes.

Também possui validações de entrada e tratamento de exceções para apresentar mensagens compreensíveis ao cliente da API.

## 4. Regras de negócio

1. **E-mail exclusivo:** não é permitido cadastrar um professor com um e-mail já utilizado por outro professor. A atualização também deve impedir conflitos com o e-mail de outro registro.
2. **Departamento válido:** o departamento informado no cadastro ou na atualização deve existir.
3. **Proteção de departamentos:** não é permitido excluir um departamento que possua professores vinculados.
4. **Dados obrigatórios:** os campos definidos como obrigatórios devem ser preenchidos corretamente, e o e-mail deve possuir formato válido.

## 5. Relacionamento entre as entidades

Um departamento pode estar associado a vários professores, enquanto cada professor pertence a um departamento.

O relacionamento é representado por:

- `@OneToMany` na entidade `Departamento`.
- `@ManyToOne` na entidade `Professor`.

Esse relacionamento permite associar vários professores a um mesmo departamento e consultar os vínculos entre as entidades.

## 6. Organização do projeto

O código é organizado em camadas:

- **Model:** representa as entidades `ProfessorModel` e `DepartamentoModel`.
- **Repository:** realiza o acesso ao banco de dados por meio do Spring Data JPA.
- **Service:** concentra as regras de negócio e as operações da aplicação.
- **Controller:** disponibiliza os endpoints da API REST.
- **DTO:** define os objetos de entrada e saída utilizados pela API.
- **Exception:** centraliza o tratamento de exceções.
- **Static:** contém os arquivos da interface web, localizados em `src/main/resources/static`.

## 7. Pré-requisitos

Para executar o projeto, é necessário ter instalado:

- JDK 21.
- PostgreSQL.
- Uma IDE ou editor de código, como Visual Studio Code ou IntelliJ IDEA.

## 8. Configuração do banco de dados

Crie um banco de dados PostgreSQL chamado `sistema_professores`.

Configure a URL de conexão, o usuário e a senha no arquivo:

`src/main/resources/application.properties`

Não publique credenciais reais no GitHub. Utilize as configurações apropriadas para seu ambiente e mantenha dados sensíveis fora do repositório.

A estrutura das tabelas é gerenciada pelo Hibernate conforme as configurações da aplicação.

## 9. Como executar o projeto

Abra o terminal na pasta raiz do projeto.

No Windows, execute:

```powershell
.\mvnw.cmd clean
```

Depois, inicie a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível, por padrão, em:

http://localhost:8080

A interface web poderá ser acessada pelo navegador em:

http://localhost:8080/

## 10. Documentação da API — Swagger

Com a aplicação em execução, acesse:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Documentação OpenAPI em JSON:** http://localhost:8080/v3/api-docs

O Swagger permite visualizar os endpoints documentados e executar requisições para testar a API.

## 11. Principais endpoints

### 11.1. Professores

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/professores` | Lista os professores |
| GET | `/professor/{id}` | Busca professor por ID |
| GET | `/professores/nome/{nome}` | Pesquisa por nome |
| GET | `/professores/area/{area}` | Pesquisa por área |
| GET | `/professores/departamento/{departamentoId}` | Lista por departamento |
| POST | `/professores` | Cadastra professor |
| PUT | `/professor/{id}` | Atualiza professor |
| DELETE | `/professor/{id}` | Exclui professor |

### 11.2. Departamentos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/departamentos` | Lista departamentos |
| GET | `/departamentos/{id}` | Busca departamento por ID |
| POST | `/departamentos` | Cadastra departamento |
| PUT | `/departamentos/{id}` | Atualiza departamento |
| DELETE | `/departamentos/{id}` | Exclui departamento |

## 12. Exemplo de cadastro de professor

**Requisição:** `POST /professores`

```json
{
  "nome": "Ana Silva",
  "email": "ana@exemplo.com",
  "area": "Banco de Dados",
  "telefone": "86999999999",
  "departamentoId": 1
}
```

O campo `departamentoId` deve corresponder a um departamento existente no banco de dados.

## 13. Códigos HTTP

| Código | Significado | Exemplo |
|---|---|---|
| `200 OK` | Operação concluída | Consulta ou atualização realizada |
| `201 Created` | Recurso criado | Cadastro de professor |
| `204 No Content` | Operação concluída sem corpo de resposta | Exclusão de professor |
| `400 Bad Request` | Requisição inválida | Campos obrigatórios ausentes ou JSON inválido |
| `404 Not Found` | Recurso não encontrado | Professor ou departamento inexistente |
| `409 Conflict` | Conflito com uma regra de negócio | E-mail duplicado ou departamento com professores vinculados |

## 14. Testes

Os endpoints podem ser testados pelo Swagger UI ou pelo Insomnia.

Os testes devem contemplar as operações de cadastro, consulta, atualização e exclusão, além das validações de entrada e das regras de negócio.

A interface web também permite testar as principais operações de gerenciamento de professores.

## 15. Vídeo de demonstração

O vídeo demonstra a execução do sistema, a interface web e as principais funcionalidades da aplicação, incluindo cadastro, consulta, edição e exclusão de professores, além da documentação da API.

**Link do vídeo:** https://youtu.be/2HLbfHMh92g

## 16. Autor

**Marcus Mikael Rodrigues Vieira**
