package com.security.jwt.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank //Que no este vacio ni sea null
    @Column(unique = true,nullable = false)
    private String nombreUsuario;

    @NotBlank //Que no este vacio ni sea null
    @Column(unique = true,nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    public Usuario(String nombreUsuario, String password, Role role) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.role = role;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public @NotBlank String getNombreUsuario() {
        return nombreUsuario;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
