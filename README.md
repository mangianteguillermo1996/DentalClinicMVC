# DentalClinicMVC 🦷

Aplicación web desarrollada con **Spring Boot** para la gestión de una clínica odontológica.  
Permite administrar pacientes, odontólogos y turnos, incorporando autenticación mediante **JWT** y arquitectura basada en capas.

## 🚀 Tecnologías utilizadas
- Java 17+
- Spring Boot
- Spring MVC
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- Maven
- Base de datos H2 / MySQL

## 🔐 Seguridad
El proyecto implementa autenticación **stateless** usando JWT:
- Endpoint `/auth/login` para obtener token.
- Filtro `JwaAuthenticationFilter` para validar tokens en cada request.
- `SecurityConfig` define accesos públicos y protegidos.
- `JwtService` gestiona creación y verificación de tokens.

## 📌 Funcionalidades principales
- CRUD de pacientes
- CRUD de odontólogos
- CRUD de turnos
- Validaciones y manejo de excepciones
- Arquitectura MVC con servicios y DTOs
- Documentación clara y estructura escalable

## 🏗️ Arquitectura
- **controller** → manejo de endpoints  
- **service** → lógica de negocio  
- **repository** → acceso a datos con JPA  
- **entity** → modelos persistentes  
- **dto** → transporte de datos  
- **auth/configuration** → seguridad y autenticación

## 📦 Ejecución
```bash
mvn spring-boot:run
