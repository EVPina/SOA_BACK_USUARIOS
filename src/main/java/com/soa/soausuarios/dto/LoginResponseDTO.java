package com.soa.soausuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    @Schema(description = "Token de acceso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    @Schema(description = "Token de refresco", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
    @Schema(description = "ID de usuario", example = "a1b2c3d4-...")
    private String id;
    @Schema(description = "Nombre de usuario", example = "usuario123")
    private String username;
    @Schema(description = "Rol del usuario", example = "ADMIN")
    private String rol;
    @Schema(description = "Nombre completo del usuario", example = "John Doe")
    private String nombreCompleto;
}
