package com.soa.soausuarios.mapper;

import org.springframework.stereotype.Component;

import com.soa.soausuarios.dto.HistorialResponseDTO;
import com.soa.soausuarios.entity.HistorialAcceso;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistorialMapper {
    
    public HistorialResponseDTO toResponseDTO(HistorialAcceso historial) {
        if (historial == null) {
            return null;
        }
        
        return HistorialResponseDTO.builder()
                .id(historial.getId())
                .usuarioId(historial.getUsuario().getId())
                .usuarioNombre(historial.getUsuario().getUsername())
                .fechaHora(historial.getFechaHora())
                .ipAddress(historial.getIpAddress())
                .accion(historial.getAccion())
                .build();
    }
    
    public List<HistorialResponseDTO> toResponseDTOList(List<HistorialAcceso> historiales) {
        if (historiales == null) {
            return null;
        }
        
        return historiales.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}