package com.soa.soausuarios.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UsuarioResponseDTO {
    private UUID id;
    private String username;
    private String nombreCompleto;
    private String email;
    private String rol;
    private String estado;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime createdAt;
}