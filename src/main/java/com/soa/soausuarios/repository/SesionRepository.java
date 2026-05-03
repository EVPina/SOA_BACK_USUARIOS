package com.soa.soausuarios.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soa.soausuarios.entity.Sesion;
import com.soa.soausuarios.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, UUID> {
    
    Optional<Sesion> findByJwtIdAndActivaTrue(String jwtId);
    
    List<Sesion> findByUsuarioAndActivaTrue(Usuario usuario);
    
    @Modifying
    @Transactional
    @Query("UPDATE Sesion s SET s.activa = false WHERE s.usuario = :usuario AND s.activa = true")
    void cerrarTodasSesionesActivas(Usuario usuario);
    
    @Modifying
    @Transactional
    @Query("UPDATE Sesion s SET s.activa = false WHERE s.expiresAt < :now AND s.activa = true")
    void cerrarSesionesExpiradas(LocalDateTime now);
}