package com.security.jwt.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
public class LoginUsuarioDto {

    @Schema(description = "Nombre del Usuario",example = "Joe")
    private String nombre;

    @Schema(description = "La contraseña del usuario", example = "example")
    private String password;

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }
}
