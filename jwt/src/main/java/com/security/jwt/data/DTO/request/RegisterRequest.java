package com.security.jwt.data.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la solicitud de registro de un nuevo usuario.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud para registrar un nuevo usuario")
public class RegisterRequest {

    /**
     * Nombre del usuario.
     */
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    /**
     * Apellido del usuario.
     */
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    /**
     * Correo electrónico del usuario.
     */
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    /**
     * Contraseña del usuario.
     */
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;
}
