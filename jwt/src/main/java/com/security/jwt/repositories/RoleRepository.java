package com.security.jwt.repositories;


import com.security.jwt.entities.Role;
import com.security.jwt.enums.RoleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNombre(RoleList nombre);
}
