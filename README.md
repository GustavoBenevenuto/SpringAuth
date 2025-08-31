# SpringAuth - Authentication System with Spring Boot and JWT

## Description

SpringAuth is an authentication and authorization project in Java using **Spring Boot**, **Spring Security**, **JWT**, and **PostgreSQL**.  
It allows:

- Registration of new users (ADMIN can create users)
- User login with JWT
- Role-based access control (USER and ADMIN)
- Persistence in PostgreSQL database
- Local environment setup with Docker

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL 16
- Docker
- Lombok
- Hibernate / JPA

## Executando o Projeto

1. Start PostgreSQL with Docker Compose:

`docker-compose up -d`

2. Build and run the Spring Boot:

```
./mvnw clean install
./mvnw spring-boot:run
```