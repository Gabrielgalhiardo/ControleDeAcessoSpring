package com.senai.controle_de_acesso_spring.infrastructure.security.filters;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.UsuarioRepository;
import com.senai.controle_de_acesso_spring.infrastructure.security.authentication.UserDetailsImpl;
import com.senai.controle_de_acesso_spring.infrastructure.security.config.SecurityConfig;
import com.senai.controle_de_acesso_spring.infrastructure.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class FiltroDeAutenticacao extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checarSeARotaDoEndpointNaoEPublica(request)) {
        String token = recuperarToken(request);
            if (token != null) {
                if (jwtUtil.validateToken(token)) {

                    String username = jwtUtil.extractUsername(token);
                    Usuario usuario = usuarioRepository.findByEmailOrCpf(username)
                                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

                    UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean checarSeARotaDoEndpointNaoEPublica(HttpServletRequest request){
        String uri = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_QUE_NAO_REQUEREM_AUTENTICACAO).contains(uri);
    }

    private String recuperarToken(HttpServletRequest request){
        String cabecalho = request.getHeader("Authorization");
        if (cabecalho != null && cabecalho.startsWith("Bearer ")) {
            return cabecalho.replace("Bearer ", "");
        }
        return null;
    }
}
