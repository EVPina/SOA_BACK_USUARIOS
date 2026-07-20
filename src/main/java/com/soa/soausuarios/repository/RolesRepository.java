package com.soa.soausuarios.repository;

import com.soa.soausuarios.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RolesRepository extends JpaRepository<Roles, UUID> {
    Optional<Roles> findByNombre(String nombre);
}
