package com.soa.soausuarios.mapper;

import org.springframework.stereotype.Component;

import com.soa.soausuarios.dto.UsuarioRequestDTO;
import com.soa.soausuarios.dto.UsuarioResponseDTO;
import com.soa.soausuarios.entity.Usuario;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {
    
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null) return null;
        
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombreCompleto(usuario.getNombreCompleto())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .estado(usuario.getEstado())
                .ultimoAcceso(usuario.getUltimoAcceso())
                .createdAt(usuario.getCreatedAt())
                .build();
    }
    
    public List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) return null;
        
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol() != null ? dto.getRol().toUpperCase() : "CAJERO");
        return usuario;
    }
}