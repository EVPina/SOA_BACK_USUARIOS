package com.soa.soausuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String refreshToken;
    private String username;
    private String rol;
    private String nombreCompleto;
}
