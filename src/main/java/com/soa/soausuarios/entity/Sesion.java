package com.soa.soausuarios.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "sesiones", schema = "public")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Column(name = "jwt_id")
    private String jwtId;
    
    @Column(name = "ip_address")
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