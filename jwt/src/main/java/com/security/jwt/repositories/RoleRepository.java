package com.security.jwt.repositories;


import com.security.jwt.entities.RolEntidad;
import com.security.jwt.enums.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RolEntidad, Long> {
    //Optional<RolEntidad> findByNombre(RolUsuarioEnum nombre);
    RolEntidad findByNombre(RolUsuarioEnum rolUsuarioEnum);

}
