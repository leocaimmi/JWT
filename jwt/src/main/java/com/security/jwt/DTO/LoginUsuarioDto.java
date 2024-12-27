package com.security.jwt.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUsuarioDto {

    private String nombre;
    private String password;

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }
}
