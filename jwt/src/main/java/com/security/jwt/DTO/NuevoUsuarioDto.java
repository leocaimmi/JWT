package com.security.jwt.DTO;

import lombok.*;

@Getter
@Setter
@Builder
public class NuevoUsuarioDto {
    private String nombre;
    private String password;

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }
}
