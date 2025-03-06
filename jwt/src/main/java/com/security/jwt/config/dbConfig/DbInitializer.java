package com.security.jwt.config.dbConfig;

import com.security.jwt.entities.RolEntidad;
import com.security.jwt.enums.RolUsuarioEnum;
import com.security.jwt.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer {

    private final RoleRepository roleRepository;

    @Autowired
    public DbInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
       // initRoles();
    }
    // Inicialización de Roles
    public void initRoles() {
        if (roleRepository.findByNombre(RolUsuarioEnum.ADMINISTRADOR) == null) {
            roleRepository.save(new RolEntidad(RolUsuarioEnum.ADMINISTRADOR));
        }
        if (roleRepository.findByNombre(RolUsuarioEnum.USUARIO) == null) {
            roleRepository.save(new RolEntidad(RolUsuarioEnum.USUARIO));
        }

    }

}
