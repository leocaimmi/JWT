# Implementación de JWT

Este proyecto demuestra cómo implementar autenticación y autorización utilizando JSON Web Tokens (JWT) con Spring Boot 3 y Spring Security 6.

## Requisitos previos

Antes de comenzar, asegúrate de tener instalados los siguientes elementos:
- Java 17+
- Maven 3.8+
- IDE utilizado IntelliJ IDEA.

## Características principales

- Autenticación basada en JWT
- Configuración de seguridad con Spring Security 6
- Controladores RESTful para autenticación y autorización
- Documentación de API con Swagger

## Estructura del proyecto

```plaintext
src
└── main
    └── java
        └── com.security.jwt
            ├── config
            │   ├── SwaggerConfig
            ├── security
            │   ├── JwtAuthenticationFilter
            │   ├── JwtService
            │   ├── RestAuthenticationEntryPoint
            │   ├── SecurityConfig
            │   ├── ApplicationConfig
            ├── controller
            │   └── AuthController
            ├── data
            │   ├── DTO
            │   │   ├── request
            │   │   │   ├── AuthenticationRequest
            │   │   │   └── RegisterRequest
            │   │   └── response
            │   │       └── AuthenticationResponse
            │   ├── mappers
            ├── model
            │   └── Usuario
            ├── repository
            │   └── UsuarioRepository
            ├── services
            │   └── AuthService
            ├── utils
            │   └── Role
resources
└── application.properties
```
## Configuración

1. **ApplicationConfig**  
   - Proporciona la configuración principal de la aplicación.

2. **SwaggerConfig**  
   - Configuración para la documentación de APIs con Swagger.

3. **JwtService**  
   - Gestiona la creación y validación de tokens JWT.

4. **JwtAuthenticationFilter**  
   - Aplica la lógica de autenticación para cada solicitud.

5. **SecurityConfig**  
   - Configuración de seguridad para la aplicación.

## Cómo ejecutar el proyecto

1. Clona este repositorio:  
   ```bash
   git clone https://github.com/leocaimmi/JWT.git
   cd JWT
   mvn clean install
   mvn spring-boot:run
  

   

