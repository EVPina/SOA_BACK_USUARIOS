package com.soa.soausuarios.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class UsuarioResponseDTO {

    @Schema(description = "ID del usuario", example = "1")
    private UUID id;
    @Schema(description = "Nombre de usuario", example = "usuario123")
    private String username;
    @Schema(description = "Nombre completo del usuario", example = "John Doe")
    private String nombreCompleto;
    @Schema(description = "Email del usuario", example = "john.doe@example.com")
    private String email;
    @Schema(description = "Rol del usuario", example = "ADMIN") 
    private String rol;
    @Schema(description = "Estado del usuario", example = "ACTIVO")
    private String estado;
    @Schema(description = "Fecha y hora del último acceso", example = "2023-01-01T12:00:00")
    private LocalDateTime ultimoAcceso;
    @Schema(description = "Fecha de creación", example = "2023-01-01T12:00:00")
    private LocalDateTime createdAt;
}