package com.soa.soausuarios.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soa.soausuarios.dto.UsuarioRequestDTO;
import com.soa.soausuarios.dto.UsuarioResponseDTO;
import com.soa.soausuarios.entity.Usuario;
import com.soa.soausuarios.mapper.UsuarioMapper;
import com.soa.soausuarios.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    
    public UsuarioService(UsuarioRepository usuarioRepository, 
                          PasswordEncoder passwordEncoder,
                          UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }
    
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return usuarioMapper.toResponseDTOList(usuarioRepository.findAll());
    }
    
    public UsuarioResponseDTO getUsuarioById(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }
    
    public UsuarioResponseDTO getUsuarioByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }
    
    @Transactional
    public UsuarioResponseDTO createUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }
        
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setEstado("ACTIVO");
        
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(saved);
    }
    
    @Transactional
    public UsuarioResponseDTO updateUsuario(UUID id, UsuarioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setNombreCompleto(request.getNombreCompleto());
        usuario.setEmail(request.getEmail());
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        
        Usuario updated = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(updated);
    }
    
    @Transactional
    public void deleteUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }
    
    @Transactional
    public void cambiarEstado(UUID id, String estado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(estado.toUpperCase());
        usuarioRepository.save(usuario);
    }
}