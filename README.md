# FIAP Product API with Ports and Adapters

Este projeto é uma API de produtos desenvolvida utilizando a arquitetura Ports and Adapters (Hexagonal Architecture), promovendo desacoplamento e facilidade de manutenção.

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Maven
- JUnit
- H2 Database

## Como Executar

1. Clone o repositório:
    ```bash
    git clone https://github.com/sonnesen/fiap-product-api-with-ports-and-adapters.git
    cd fiap-product-api-with-ports-and-adapters
    ```
2. Compile e execute:
    ```bash
    ./mvnw spring-boot:run
    ```
3. Acesse a API em: `http://localhost:8080`

## Endpoints Principais

- `GET /categories` — Lista todas as categorias de produtos
- `POST /categories` — Cria uma nova categoria
- `GET /categories/{id}` — Detalha uma categria
- `PUT /categories/{id}` — Atualiza uma categoria
- `DELETE /categories/{id}` — Remove uma categoria

## Testes

Execute os testes com:
```bash
./mvnw test
```

## Estrutura do Projeto

- **application/domain**: regras de negócio e entidades
- **application/ports**: casos de uso
- **application/service**: serviços responsáveis por orquestrar e executar as regras de negócio
- **infrastructure/adapters**: interfaces externas (REST,Graphql, banco de dados)

## Contribuição

Pull requests são bem-vindos! Siga o padrão de código e adicione testes.

## Licença

Este projeto está licenciado sob a licença MIT.