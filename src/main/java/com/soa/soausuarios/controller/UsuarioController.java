package com.soa.soausuarios.controller;

import com.soa.soausuarios.dto.UsuarioRequestDTO;
import com.soa.soausuarios.dto.UsuarioResponseDTO;
import com.soa.soausuarios.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para gestionar los usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios listados exitosamente")
    })
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioResponseDTO> crear(@RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.createUsuario(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable UUID id, 
                                                          @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/rol")
    @Operation(summary = "Cambiar rol de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol de usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> cambiarRol(@PathVariable UUID id, 
                                                          @RequestParam String rol) {
        usuarioService.cambiarRol(id, rol);
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }
}