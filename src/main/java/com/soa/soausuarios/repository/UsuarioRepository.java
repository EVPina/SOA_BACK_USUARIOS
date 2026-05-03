package com.soa.soausuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soa.soausuarios.entity.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}