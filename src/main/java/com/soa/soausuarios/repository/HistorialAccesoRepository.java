package com.soa.soausuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.soausuarios.entity.HistorialAcceso;
import com.soa.soausuarios.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistorialAccesoRepository extends JpaRepository<HistorialAcceso, UUID> {
    
    List<HistorialAcceso> findByUsuarioOrderByFechaHoraDesc(Usuario usuario);
    
    List<HistorialAcceso> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    long countByUsuarioAndAccion(Usuario usuario, String accion);
}
