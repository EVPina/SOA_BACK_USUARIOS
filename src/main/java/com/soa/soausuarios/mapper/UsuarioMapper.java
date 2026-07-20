package com.soa.soausuarios.mapper;

import org.springframework.stereotype.Component;

import com.soa.soausuarios.dto.UsuarioRequestDTO;
import com.soa.soausuarios.dto.UsuarioResponseDTO;
import com.soa.soausuarios.entity.Usuario;

import java.util.List;
import java.util.stream.Collectors;

import com.soa.soausuarios.repository.RolesRepository;
import com.soa.soausuarios.entity.Roles;

@Component
public class UsuarioMapper {
    
    private final RolesRepository rolesRepository;

    public UsuarioMapper(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        
        if (usuario == null) return null;
        
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombreCompleto(usuario.getNombreCompleto())
                .email(usuario.getEmail())
                .rol(usuario.getRol().getNombre())
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
        String rolNombre = dto.getRol() != null ? dto.getRol().toUpperCase() : "CAJERO";
        Roles rol = rolesRepository.findByNombre(rolNombre)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));
        usuario.setRol(rol);
        return usuario;
    }
}