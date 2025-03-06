package com.security.jwt.entities;

import com.security.jwt.enums.RolUsuarioEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "roles")
public class RolEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RolUsuarioEnum nombre;

    public RolEntidad() {

    }

    public RolEntidad(RolUsuarioEnum rolUsuarioEnum) {
    }
}

