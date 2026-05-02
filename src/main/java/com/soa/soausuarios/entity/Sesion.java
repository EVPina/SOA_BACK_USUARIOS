package com.soa.soausuarios.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sesiones", schema = "usuarios_db")
public class Sesion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "jwt_id", length = 64)
    private String jwtId;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "ultima_actividad")
    private LocalDateTime ultimaActividad;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    private Boolean activa = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}