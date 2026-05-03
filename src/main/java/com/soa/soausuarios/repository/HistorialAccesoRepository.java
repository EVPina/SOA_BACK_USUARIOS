package com.soa.soausuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soa.soausuarios.entity.HistorialAcceso;
import com.soa.soausuarios.entity.Usuario;

import java.util.List;
import java.util.UUID;

public interface HistorialAccesoRepository extends JpaRepository<HistorialAcceso, UUID> {
    List<HistorialAcceso> findByUsuarioOrderByFechaHoraDesc(Usuario usuario);
}