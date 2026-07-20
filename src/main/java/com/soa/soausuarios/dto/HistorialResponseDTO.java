package com.soa.soausuarios.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class HistorialResponseDTO {
    @Schema(description = "ID del historial", example = "1")
    private UUID id;
    @Schema(description = "ID del usuario", example = "1")
    private UUID usuarioId;
    @Schema(description = "Nombre del usuario", example = "John Doe")
    private String usuarioNombre;
    @Schema(description = "Fecha y hora del historial", example = "2023-01-01T12:00:00")
    private LocalDateTime fechaHora;
    @Schema(description = "Dirección IP", example = "192.168.1.1")
    private String ipAddress;
    @Schema(description = "Acción realizada", example = "Iniciar sesión")
    private String accion;
    @Schema(description = "Fecha de creación", example = "2023-01-01T12:00:00")
    private LocalDateTime createdAt;
}