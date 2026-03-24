📧 Email Microservice
Microsserviço responsável pelo envio de e-mails transacionais, integrado com RabbitMQ como broker de mensageria para comunicação assíncrona entre serviços.

🚀 Tecnologias

Java 17+
Spring Boot 3.x
Spring AMQP (RabbitMQ)
JavaMailSender (SMTP)
Maven
Docker / Docker Compose


🏗️ Arquitetura
[Outros Microsserviços]
        |
        | publica mensagem
        ↓
   [RabbitMQ Exchange]
        |
        | roteia para fila
        ↓
   [Queue: email.queue]
        |
        | consome
        ↓
 [Email Microservice]
        |
        | envia via SMTP
        ↓
   [Destinatário 📩]
O serviço atua como consumer de uma fila RabbitMQ. Outros microsserviços publicam eventos de e-mail na exchange, e este serviço processa e realiza o envio via SMTP.

⚙️ Configuração
Variáveis de Ambiente
Crie um arquivo .env na raiz do projeto ou configure as variáveis no application.properties:
properties# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Fila
rabbitmq.queue.name=email.queue
rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key

# SMTP (exemplo com Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

📨 Payload da Mensagem
O serviço espera receber o seguinte JSON na fila RabbitMQ:
json{
  "to": "destinatario@email.com",
  "subject": "Assunto do e-mail",
  "body": "Conteúdo do e-mail"
}
CampoTipoObrigatórioDescriçãotoString✅ SimE-mail do destinatáriosubjectString✅ SimAssunto do e-mailbodyString✅ SimCorpo do e-mail

▶️ Como Executar
Pré-requisitos

Java 17+
Maven 3.8+
Docker e Docker Compose

1. Subir o RabbitMQ via Docker
bashdocker-compose up -d
Ou manualmente:
bashdocker run -d --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:3-management
Acesse o painel de gerenciamento em: http://localhost:15672

Credenciais padrão: guest / guest

2. Compilar e executar o projeto
bash./mvnw clean install
./mvnw spring-boot:run

🐳 Docker Compose
yamlversion: '3.8'

services:
  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest

  email-service:
    build: .
    container_name: email-service
    ports:
      - "8080:8080"
    environment:
      SPRING_RABBITMQ_HOST: rabbitmq
      SPRING_MAIL_USERNAME: seu-email@gmail.com
      SPRING_MAIL_PASSWORD: sua-senha-de-app
    depends_on:
      - rabbitmq
```

---

## 📁 Estrutura do Projeto
```
src/
└── main/
    └── java/
        └── com/yourcompany/emailservice/
            ├── config/
            │   └── RabbitMQConfig.java       # Configuração de filas e exchanges
            ├── consumer/
            │   └── EmailConsumer.java        # Listener da fila RabbitMQ
            ├── dto/
            │   └── EmailRequestDTO.java      # DTO da mensagem
            ├── service/
            │   └── EmailService.java         # Lógica de envio de e-mail
            └── EmailServiceApplication.java  # Classe principal

🤝 Como Publicar uma Mensagem (Producer externo)
Exemplo de como outro microsserviço pode publicar um evento para esta fila:
java@Autowired
private RabbitTemplate rabbitTemplate;

public void sendEmailEvent(EmailRequestDTO dto) {
    rabbitTemplate.convertAndSend(
        "email.exchange",
        "email.routing-key",
        dto
    );
}

📄 Licença
Este projeto está sob a licença MIT.

Quer que eu ajuste algo, como adicionar seções de testes, tratamento de erros, ou mudar alguma nomenclatura para bater com o seu código real?nao estou usando o dokcer esotu usando o claud ampq15:16Sem problema! Aqui está o README atualizado usando CloudAMQP ao invés de Docker:

📧 Email Microservice
Microsserviço responsável pelo envio de e-mails transacionais, integrado com RabbitMQ via CloudAMQP como broker de mensageria para comunicação assíncrona entre serviços.

🚀 Tecnologias

Java 17+
Spring Boot 3.x
Spring AMQP (RabbitMQ)
CloudAMQP (RabbitMQ como serviço na nuvem)
JavaMailSender (SMTP)
Maven


🏗️ Arquitetura
[Outros Microsserviços]
        |
        | publica mensagem
        ↓
  [CloudAMQP - Exchange]
        |
        | roteia para fila
        ↓
   [Queue: email.queue]
        |
        | consome
        ↓
 [Email Microservice]
        |
        | envia via SMTP
        ↓
   [Destinatário 📩]
O serviço atua como consumer de uma fila no CloudAMQP. Outros microsserviços publicam eventos na exchange, e este serviço processa e realiza o envio via SMTP.

⚙️ Configuração
Variáveis de Ambiente
Configure as variáveis no application.properties:
properties# CloudAMQP - cole a URL fornecida no painel do CloudAMQP
spring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

# Fila
rabbitmq.queue.name=email.queue
rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key

# SMTP (exemplo com Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

💡 A URL amqps://... é encontrada no painel do CloudAMQP em AMQP URL dentro do seu instance.


📨 Payload da Mensagem
O serviço espera receber o seguinte JSON na fila RabbitMQ:
json{
  "to": "destinatario@email.com",
  "subject": "Assunto do e-mail",
  "body": "Conteúdo do e-mail"
}
CampoTipoObrigatórioDescriçãotoString✅ SimE-mail do destinatáriosubjectString✅ SimAssunto do e-mailbodyString✅ SimCorpo do e-mail

▶️ Como Executar
Pré-requisitos

Java 17+
Maven 3.8+
Conta no CloudAMQP com uma instância criada

1. Configurar o CloudAMQP

Acesse cloudamqp.com e crie uma conta
Crie uma nova instância (o plano Little Lemur é gratuito)
Copie a AMQP URL e cole no application.properties

2. Compilar e executar o projeto
bash./mvnw clean install
./mvnw spring-boot:run
```

---

## 📁 Estrutura do Projeto
```
src/
└── main/
    └── java/
        └── com/yourcompany/emailservice/
            ├── config/
            │   └── RabbitMQConfig.java       # Configuração de filas e exchanges
            ├── consumer/
            │   └── EmailConsumer.java        # Listener da fila RabbitMQ
            ├── dto/
            │   └── EmailRequestDTO.java      # DTO da mensagem
            ├── service/
            │   └── EmailService.java         # Lógica de envio de e-mail
            └── EmailServiceApplication.java  # Classe principal

🤝 Como Publicar uma Mensagem (Producer externo)
Exemplo de como outro microsserviço pode publicar um evento para esta fila:
java@Autowired
private RabbitTemplate rabbitTemplate;

public void sendEmailEvent(EmailRequestDTO dto) {
    rabbitTemplate.convertAndSend(
        "email.exchange",
        "email.routing-key",
        dto
    );
}

🔗 Dependência Maven
Certifique-se de ter a dependência no pom.xml:
xml<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

📄 Licença
Este projeto está sob a licença MIT.

Quer que eu adicione mais alguma seção, como tratamento de erros, variáveis de ambiente com .env, ou ajuste algum nome de classe/pacote para bater com o seu projeto real?eu tenho uma classe user que eu mando o nome e o email ai meu micro de email faz o papel de consumer15:17Ótimo detalhe! Vou atualizar o payload e toda a documentação para refletir a classe User corretamente.

📧 Email Microservice
Microsserviço responsável pelo envio de e-mails transacionais, integrado com RabbitMQ via CloudAMQP como broker de mensageria para comunicação assíncrona entre serviços.

🚀 Tecnologias

Java 17+
Spring Boot 3.x
Spring AMQP (RabbitMQ)
CloudAMQP (RabbitMQ como serviço na nuvem)
JavaMailSender (SMTP)
Maven


🏗️ Arquitetura
[Microsserviço Producer]
        |
        | publica { name, email }
        ↓
  [CloudAMQP - Exchange]
        |
        | roteia para fila
        ↓
   [Queue: email.queue]
        |
        | consome (Consumer)
        ↓
 [Email Microservice]
        |
        | envia e-mail ao usuário
        ↓
     [User 📩]
O serviço atua exclusivamente como consumer. Outro microsserviço publica os dados do usuário na exchange, e este serviço processa e realiza o envio do e-mail.

⚙️ Configuração
application.properties
properties# CloudAMQP - cole a URL fornecida no painel do CloudAMQP
spring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

# Fila
rabbitmq.queue.name=email.queue
rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key

# SMTP (exemplo com Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

💡 A URL amqps://... é encontrada no painel do CloudAMQP em AMQP URL dentro da sua instância.


👤 Modelo — Classe User
O microsserviço producer publica os dados do usuário na fila. A classe User esperada por este consumer:
javapublic class User {
    private String name;
    private String email;

    // getters e setters
}
Payload JSON na fila
json{
  "name": "João Silva",
  "email": "joao.silva@email.com"
}
```

| Campo   | Tipo     | Obrigatório | Descrição               |
|---------|----------|-------------|-------------------------|
| `name`  | `String` | ✅ Sim      | Nome do usuário         |
| `email` | `String` | ✅ Sim      | E-mail do destinatário  |

---

## 🔁 Fluxo do Consumer
```
RabbitMQ (CloudAMQP)
        |
        | @RabbitListener
        ↓
  EmailConsumer.java
        |
        | chama
        ↓
  EmailService.java
        |
        | monta e envia o e-mail para user.getEmail()
        ↓
     Usuário 📩
Exemplo — EmailConsumer.java
java@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void listen(User user) {
        emailService.sendEmail(user);
    }
}
Exemplo — EmailService.java
java@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Bem-vindo, " + user.getName() + "!");
        message.setText("Olá, " + user.getName() + "! Seu cadastro foi realizado com sucesso.");

        mailSender.send(message);
    }
}
```

---

## 📁 Estrutura do Projeto
```
src/
└── main/
    └── java/
        └── com/yourcompany/emailservice/
            ├── config/
            │   └── RabbitMQConfig.java       # Configuração de filas e exchanges
            ├── consumer/
            │   └── EmailConsumer.java        # Listener da fila RabbitMQ
            ├── model/
            │   └── User.java                 # Modelo recebido na fila
            ├── service/
            │   └── EmailService.java         # Lógica de envio de e-mail
            └── EmailServiceApplication.java  # Classe principal

🔗 Dependências Maven (pom.xml)
xml<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

📄 Licença
Este projeto está sob a licença MIT.

Quer que eu gere isso como arquivo README.md para download, ou adicione mais alguma seção como tratamento de erros ou status de envio?um micro de user na verdade15:17Entendido! Então você tem dois microsserviços: o de User (producer) e o de Email (consumer). Vou fazer um README para cada um!Me conta para eu montar certinho! 😊P: Para qual(is) microsserviço(s) você quer o README? (Selecione todas que se aplicam)
R: Os doisP: O micro de User também usa Spring Boot?
R: Sim, Spring Boot15:18Perfeito! Vou gerar os dois READMEs completos e separados!

👤 User Microservice
Microsserviço responsável pelo gerenciamento de usuários. Ao cadastrar um novo usuário, publica os dados na fila do RabbitMQ via CloudAMQP para que o microsserviço de e-mail realize o envio da mensagem de boas-vindas.

🚀 Tecnologias

Java 17+
Spring Boot 3.x
Spring AMQP (RabbitMQ)
CloudAMQP (RabbitMQ como serviço na nuvem)
Spring Data JPA
Maven


🏗️ Arquitetura
  [Client / API REST]
        |
        | POST /users
        ↓
  [UserController]
        |
        | salva no banco
        ↓
   [UserRepository]
        |
        | publica { name, email }
        ↓
  [CloudAMQP - Exchange]
        |
        | roteia para fila
        ↓
   [Queue: email.queue]  →  [Email Microservice 📩]

⚙️ Configuração
application.properties
properties# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/users_db
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update

# CloudAMQP
spring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

# Fila
rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key

📨 Endpoint
POST /users — Cadastrar usuário
Request Body:
json{
  "name": "João Silva",
  "email": "joao.silva@email.com"
}
Response 201 Created:
json{
  "id": "uuid-gerado",
  "name": "João Silva",
  "email": "joao.silva@email.com"
}

👤 Modelo — Classe User
java@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String email;

    // getters e setters
}

📤 Publisher — Exemplo
java@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public User save(User user) {
        User saved = userRepository.save(user);
        rabbitTemplate.convertAndSend(exchange, routingKey, saved);
        return saved;
    }
}
```

---

## 📁 Estrutura do Projeto
```
src/
└── main/
    └── java/
        └── com/yourcompany/userservice/
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

🔗 Dependências Maven (pom.xml)
xml<dependency>
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

▶️ Como Executar
bash./mvnw clean install
./mvnw spring-boot:run
```

---

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).

---
---

# 📧 Email Microservice

Microsserviço responsável pelo envio de e-mails transacionais. Atua como **consumer** da fila RabbitMQ via **CloudAMQP**, recebendo os dados do usuário publicados pelo microsserviço de User e realizando o envio do e-mail.

---

## 🚀 Tecnologias

- **Java 17+**
- **Spring Boot 3.x**
- **Spring AMQP** (RabbitMQ)
- **CloudAMQP** (RabbitMQ como serviço na nuvem)
- **JavaMailSender** (SMTP)
- **Maven**

---

## 🏗️ Arquitetura
```
[User Microservice]
        |
        | publica { name, email }
        ↓
  [CloudAMQP - Exchange]
        |
        | roteia para fila
        ↓
   [Queue: email.queue]
        |
        | @RabbitListener
        ↓
  [EmailConsumer.java]
        |
        | chama
        ↓
  [EmailService.java]
        |
        | envia via SMTP
        ↓
     [User 📩]

⚙️ Configuração
application.properties
properties# CloudAMQP
spring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

# Fila
rabbitmq.queue.name=email.queue
rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key

# SMTP (Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

💡 A URL amqps://... é encontrada no painel do CloudAMQP em AMQP URL dentro da sua instância.


👤 Modelo recebido — Classe User
javapublic class User {
    private String name;
    private String email;

    // getters e setters
}
Payload esperado na fila
json{
  "name": "João Silva",
  "email": "joao.silva@email.com"
}

🔁 Consumer — Exemplo
java@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void listen(User user) {
        emailService.sendEmail(user);
    }
}
java@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Bem-vindo, " + user.getName() + "!");
        message.setText("Olá, " + user.getName() + "! Seu cadastro foi realizado com sucesso.");
        mailSender.send(message);
    }
}
```

---

## 📁 Estrutura do Projeto
```
src/
└── main/
    └── java/
        └── com/yourcompany/emailservice/
            ├── config/
            │   └── RabbitMQConfig.java
            ├── consumer/
            │   └── EmailConsumer.java
            ├── model/
            │   └── User.java
            ├── service/
            │   └── EmailService.java
            └── EmailServiceApplication.java

🔗 Dependências Maven (pom.xml)
xml<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

▶️ Como Executar
bash./mvnw clean install
./mvnw spring-boot:run

📄 Licença
Este projeto está sob a licença MIT.

Quer que eu salve os dois como arquivos README.md separados para download? Ou precisa ajustar algum detalhe como nome de pacote, banco de dados, ou porta da aplicação?eles estao no mesmo repositorio15:18Entendido! Nesse caso vou fazer um único README que documenta os dois microsserviços dentro do mesmo repositório (monorepo).

📬 User & Email Microservices
Repositório contendo dois microsserviços que se comunicam de forma assíncrona via RabbitMQ (CloudAMQP). O microsserviço de User gerencia o cadastro de usuários e atua como producer, enquanto o microsserviço de Email consome a fila e realiza o envio do e-mail transacional.

🗂️ Estrutura do Repositório
/
├── user-service/
│   ├── src/
│   └── pom.xml
├── email-service/
│   ├── src/
│   └── pom.xml
└── README.md

🚀 Tecnologias

Java 17+
Spring Boot 3.x
Spring AMQP (RabbitMQ)
CloudAMQP (RabbitMQ como serviço na nuvem)
Spring Data JPA
JavaMailSender (SMTP)
Maven


🏗️ Arquitetura
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

📦 Microsserviços
👤 user-service — Producer
Responsável pelo cadastro de usuários. Ao salvar um novo usuário, publica os dados na exchange do RabbitMQ.
application.properties
propertiesspring.datasource.url=jdbc:postgresql://localhost:5432/users_db
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update

spring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

rabbitmq.exchange.name=email.exchange
rabbitmq.routing.key=email.routing-key
Endpoint
POST /users
json// Request
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

📧 email-service — Consumer
Responsável pelo envio de e-mails. Escuta a fila do RabbitMQ e envia o e-mail ao usuário cadastrado.
application.properties
propertiesspring.rabbitmq.addresses=amqps://usuario:senha@hostname.cloudamqp.com/vhost

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

▶️ Como Executar
Pré-requisitos

Java 17+
Maven 3.8+
Conta no CloudAMQP com instância criada

Executar o user-service
bashcd user-service
./mvnw spring-boot:run
Executar o email-service
bashcd email-service
./mvnw spring-boot:run

⚠️ Certifique-se de que ambos os serviços apontam para a mesma instância do CloudAMQP e usam o mesmo nome de exchange e routing key.


🔗 Dependências Maven
user-service:
xml<dependency>
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
email-service:
xml<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

📄 Licença
Este projeto está sob a licença MIT.
