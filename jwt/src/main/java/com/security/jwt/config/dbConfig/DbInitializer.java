package com.security.jwt.config.dbConfig;

import com.security.jwt.entities.Role;
import com.security.jwt.enums.RoleList;
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
        initRoles();
    }
    // Inicialización de Roles
    public void initRoles() {
        // Se verifica si los roles del enum no están presentes en la base de datos y se guardan
        for (RoleList role : RoleList.values()) {
            if (roleRepository.findByNombre(role).isEmpty()) {
                roleRepository.save(new Role(null, role)); // Se guarda un nuevo Role con el nombre del enum
            }
        }
    }
}
