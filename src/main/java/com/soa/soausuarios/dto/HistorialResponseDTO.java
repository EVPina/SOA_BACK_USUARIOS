package com.soa.soausuarios.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class HistorialResponseDTO {
    private UUID id;
    private UUID usuarioId;
    private String usuarioNombre;
    private LocalDateTime fechaHora;
    private String ipAddress;
    private String accion;
    private LocalDateTime createdAt;
}