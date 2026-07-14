package com.soa.soausuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Schema(description = "Nombre de usuario", example = "usuario123")
    @NotBlank(message = "El username es obligatorio")
    private String username;
    
    @Schema(description = "Contraseña", example = "contraseña123")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}