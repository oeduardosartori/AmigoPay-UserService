# AmigoPay - User Service

O **AmigoPay User Service** é um microsserviço responsável pela gestão de usuários da plataforma AmigoPay. Suas principais responsabilidades incluem:

- Criar, consultar, atualizar e remover usuários;
- Emitir eventos Kafka (`UserCreated`) para integração com outros serviços da plataforma, como carteira e pagamentos.

---

## Tecnologias e Ferramentas

- Java 17
- Spring Boot 3.5.3
    - Spring Web
    - Spring Data JPA
    - Spring Validation
    - Spring Kafka
    - Spring Security (uso mínimo)
- PostgreSQL
- Lombok
- MapStruct
- Docker Compose (para Kafka, Zookeeper e PostgreSQL)

---

## Estrutura do Projeto

O projeto adota uma arquitetura modular, orientada ao domínio, com as seguintes camadas principais:

```
com.amigopay.user
├── user → Lógica de domínio do usuário (DTOs, entidades, validações)
├── messaging → Publicação de eventos Kafka
├── common → Utilitários e enums compartilhados
├── exception → Tratamento global de exceções
├── config → Configurações e beans do Spring (Kafka, MessageSource, segurança)
```

---

## Funcionalidades

| Endpoint                 | Método | Descrição                      |
|--------------------------|--------|-------------------------------|
| `/api/v1/users`          | POST   | Criação de usuário            |
| `/api/v1/users`          | GET    | Listagem paginada de usuários |
| `/api/v1/users/{id}`     | GET    | Buscar usuário por ID         |
| `/api/v1/users/{id}`     | PUT    | Atualizar usuário por ID      |
| `/api/v1/users/{id}`     | DELETE | Remover usuário               |

> Todos os endpoints são protegidos por autenticação JWT, via API Gateway.

---

## Integração com Kafka

Ao criar um usuário com sucesso, o serviço publica o seguinte evento no Kafka:

```json
{
  "id": "UUID",
  "firstName": "John",
  "lastName": "Junior",
  "email": "john@amigopay.com",
  "createdAt": "2025-07-01T14:22:03"
}
```
- Tópico: `user.created`
- Publicador: `UserKafkaPublisher`
- Criação automática de tópicos: habilitada via configuração Spring

---

## Configuração

### application.yml (exemplo simplificado)

```yaml
spring:
datasource:
url: jdbc:postgresql://localhost:5433/userdb
username: postgres
password: postgres

kafka:
bootstrap-servers: localhost:9092
producer:
key-serializer: org.apache.kafka.common.serialization.StringSerializer
value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
admin:
auto-create: true
```
> Em ambientes de produção, recomenda-se configurar as variáveis sensíveis via `VM_OPTIONS`, `application-prod.yml` ou `docker-compose.override.yml`.

---

## Testes
- Testes unitários com `spring-boot-starter-test`
- Testes de integração com suporte ao Kafka via `spring-kafka-test`
- Cobertura de lógica de negócio e camadas de domínio

--- 

## Execução Local

### Requisitos
- Java 17
- Maven 3.8+
- Docker e Docker Compose

### Etapas
```
# Subir infraestrutura (Kafka, Zookeeper, PostgreSQL)
docker-compose up -d

# Compilar o projeto
./mvnw clean install

# Executar a aplicação
./mvnw spring-boot:run
```

---

## Próximas Etapas
- Integração com o API Gateway (`amigopay-gateway`) para autenticação e roteamento
- Criação de consumidores Kafka nos serviços dependentes (`wallet-service`, `payment-service`)

---

## Contribuidores

| Nome            | Papel                           |
| --------------- | ------------------------------- |
| Eduardo Sartori | Arquiteto e Desenvolvedor Líder |

---

## Licença
Projeto privado. Todos os direitos reservados à equipe AmigoPay.