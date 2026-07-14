package com.soa.soausuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @Schema(description = "Nombre de usuario", example = "usuario123")
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;
    
    @Schema(description = "Contraseña del usuario", example = "password123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    @Schema(description = "Nombre completo del usuario", example = "John Doe")
    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;
    
    @Schema(description = "Email del usuario", example = "john.doe@example.com")
    @Email(message = "El email debe ser válido")
    private String email;
    
    @Schema(description = "Rol del usuario", example = "ADMIN")
    private String rol;
}