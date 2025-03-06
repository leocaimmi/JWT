package com.security.jwt.entities;


import com.security.jwt.enums.RolUsuarioEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

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
    private RolEntidad rol;

    public Usuario(String nombreUsuario, String password, RolEntidad role) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = role;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public @NotBlank String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public @NotBlank String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                '}';
    }
}
