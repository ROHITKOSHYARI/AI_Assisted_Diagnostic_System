package com.rohit.diagnostic_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            log.debug("No bearer token found for path={}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = jwtService.extractClaims(header.substring(7));
            String role = claims.get("role", String.class);
            var authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("JWT authenticated subject={} role={} path={}", claims.getSubject(), role, request.getRequestURI());
        } catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            log.warn("JWT authentication failed path={} reason={}", request.getRequestURI(), ex.getClass().getSimpleName());
        }

        filterChain.doFilter(request, response);
    }
}
