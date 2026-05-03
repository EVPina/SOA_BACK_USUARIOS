package com.soa.soausuarios.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soa.soausuarios.dto.LoginRequestDTO;
import com.soa.soausuarios.dto.LoginResponseDTO;
import com.soa.soausuarios.dto.UsuarioRequestDTO;
import com.soa.soausuarios.dto.UsuarioResponseDTO;
import com.soa.soausuarios.entity.HistorialAcceso;
import com.soa.soausuarios.entity.Sesion;
import com.soa.soausuarios.entity.Usuario;
import com.soa.soausuarios.mapper.UsuarioMapper;
import com.soa.soausuarios.repository.HistorialAccesoRepository;
import com.soa.soausuarios.repository.SesionRepository;
import com.soa.soausuarios.repository.UsuarioRepository;

import java.time.LocalDateTime;

@Service
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final SesionRepository sesionRepository;
    private final HistorialAccesoRepository historialRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioDetailsService userDetailsService;
    private final UsuarioMapper usuarioMapper;
    
    public AuthService(UsuarioRepository usuarioRepository,
                       SesionRepository sesionRepository,
                       HistorialAccesoRepository historialRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UsuarioDetailsService userDetailsService,
                       UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.sesionRepository = sesionRepository;
        this.historialRepository = historialRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.usuarioMapper = usuarioMapper;
    }
    
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new RuntimeException("Usuario no está activo");
        }
        
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        String ipAddress = httpRequest.getRemoteAddr();
        
        HistorialAcceso historial = new HistorialAcceso();
        historial.setUsuario(usuario);
        historial.setFechaHora(LocalDateTime.now());
        historial.setIpAddress(ipAddress);
        historial.setAccion("LOGIN");
        historialRepository.save(historial);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getUsername());
        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        
        Sesion sesion = new Sesion();
        sesion.setUsuario(usuario);
        sesion.setJwtId(jwtService.extractJwtId(token));
        sesion.setIpAddress(ipAddress);
        sesion.setUltimaActividad(LocalDateTime.now());
        sesion.setExpiresAt(LocalDateTime.now().plusHours(24));
        sesion.setActiva(true);
        sesionRepository.save(sesion);
        
        return new LoginResponseDTO(token, refreshToken, usuario.getUsername(), usuario.getRol(), usuario.getNombreCompleto());
    }
    
    @Transactional
    public UsuarioResponseDTO register(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }
        
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setEstado("ACTIVO");
        
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(saved);
    }
}