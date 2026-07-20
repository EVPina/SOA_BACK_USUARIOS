package com.soa.soausuarios.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.soa.soausuarios.services.JwtService;
import com.soa.soausuarios.services.UsuarioDetailsService;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        
        // LOG: Ver qué header llega
        System.out.println("🔍 Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ No hay token o formato incorrecto");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        
        // Si el token es un UUID de Cliente (no tiene puntos), lo ignoramos 
        // para no lanzar excepciones de JWT mal formado.
        if (!jwt.contains(".")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String username = jwtService.extractUsername(jwt);
            System.out.println("🔍 Username extraído: " + username);
            System.out.println("🔍 Contexto actual: " + SecurityContextHolder.getContext().getAuthentication());

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("🔍 UserDetails cargado: " + userDetails.getUsername());
                System.out.println("🔍 Authorities: " + userDetails.getAuthorities());

                if (jwtService.validateToken(jwt, userDetails)) {
                    System.out.println("✅ Token válido");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✅ Autenticación establecida");
                } else {
                    System.out.println("❌ Token inválido");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error procesando JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    
}