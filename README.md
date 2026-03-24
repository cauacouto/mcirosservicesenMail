# 📬 User & Email Microservices

Repositório contendo dois microsserviços que se comunicam de forma assíncrona via **RabbitMQ (CloudAMQP)**. O microsserviço de **User** gerencia o cadastro de usuários e atua como **producer**, enquanto o microsserviço de **Email** consome a fila e realiza o envio do e-mail transacional.

---

## 🗂️ Estrutura do Repositório

```
/
├── user-service/
│   ├── src/
│   └── pom.xml
├── email-service/
│   ├── src/
│   └── pom.xml
└── README.md
```

---

## 🚀 Tecnologias

- **Java 17+**
- **Spring Boot 3.x**
- **Spring AMQP** (RabbitMQ)
- **CloudAMQP** (RabbitMQ como serviço na nuvem)
- **Spring Data JPA**
- **JavaMailSender** (SMTP)
- **Maven**

---

## 🏗️ Arquitetura

```
  [Client / API REST]
        |
        | POST /users
        ↓
┌─────────────────────┐
│   user-service      │  →  salva no banco de dados
│   (Producer)        │  →  publica { name, email } na fila
└─────────────────────┘
        |
        | CloudAMQP Exchange
        ↓
   [Queue: email.queue]
        |
        | @RabbitListener
        ↓
┌─────────────────────┐
│   email-service     │  →  consome a mensagem
│   (Consumer)        │  →  envia e-mail ao usuário
└─────────────────────┘
        |
        ↓
     [User 📩]
```

---

## 📦 Microsserviços

### 👤 user-service — Producer

Responsável pelo cadastro de usuários. Ao salvar um novo usuário, publica os dados na exchange do RabbitMQ.

#### `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/users_db
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update

spring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key
```

#### Endpoint

`POST /users`

```json
// Request
{
  "name": "João Silva",
  "email": "joao.silva@email.com"
}

// Response 201 Created
{
  "id": "uuid-gerado",
  "name": "João Silva",
  "email": "joao.silva@email.com"
}
```

#### Estrutura

```
user-service/src/main/java/com/yourcompany/userservice/
├── config/
│   └── RabbitMQConfig.java
├── controller/
│   └── UserController.java
├── model/
│   └── User.java
├── repository/
│   └── UserRepository.java
├── service/
│   └── UserService.java
└── UserServiceApplication.java
```

---

### 📧 email-service — Consumer

Responsável pelo envio de e-mails. Escuta a fila do RabbitMQ e envia o e-mail ao usuário cadastrado.

#### `application.properties`

```properties
spring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

rabbitmq.queue.name=email.queue
rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

#### Estrutura

```
email-service/src/main/java/com/yourcompany/emailservice/
├── config/
│   └── RabbitMQConfig.java
├── consumer/
│   └── EmailConsumer.java
├── model/
│   └── User.java
├── service/
│   └── EmailService.java
└── EmailServiceApplication.java
```

---

## ▶️ Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Conta no [CloudAMQP](https://www.cloudamqp.com/) com instância criada

### Executar o user-service

```bash
cd user-service
./mvnw spring-boot:run
```

### Executar o email-service

```bash
cd email-service
./mvnw spring-boot:run
```

> ⚠️ Certifique-se de que ambos os serviços apontam para a **mesma instância do CloudAMQP** e usam o mesmo nome de exchange e routing key.

---

## 🔗 Dependências Maven

**user-service:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**email-service:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

---

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).
