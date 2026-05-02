package com.soa.soausuarios.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios", schema = "usuarios_db")
public class Usuario {
      
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;
    
    @Column(length = 100)
    private String email;
    
    @Column(nullable = false, length = 20)
    private String rol; // ADMIN, GERENTE, CAJERO, COCINERO, MESERO, DELIVERY
    
    @Column(length = 20)
    private String estado; // ACTIVO, INACTIVO, BLOQUEADO
    
    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
