package com.security.jwt.entities;


import com.security.jwt.enums.RoleList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private RoleList nombre;

    public Role(Long id, RoleList nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public RoleList getNombre() {
        return nombre;
    }
}


