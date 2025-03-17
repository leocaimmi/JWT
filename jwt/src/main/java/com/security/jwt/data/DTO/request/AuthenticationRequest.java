package com.security.jwt.data.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la solicitud de autenticación de un usuario.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud para autenticar un usuario")
public class AuthenticationRequest {

    /**
     * Nombre de usuario o correo electrónico.
     */
    @Schema(description = "Nombre de usuario o correo electrónico", example = "juan.perez@email.com")
    private String username;

    /**
     * Contraseña del usuario.
     */
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;
}
