package com.senai.controle_de_acesso_spring.infrastructure.security.config;

import com.senai.controle_de_acesso_spring.infrastructure.security.filters.FiltroDeAutenticacao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] ENDPOINTS_QUE_NAO_REQUEREM_AUTENTICACAO = {
            "/",
            "/auth/**",
            "/usuarios/**"
    };
    public static final String[] ENDPOINTS_DA_AQV = {
            "/alunos/**",
            "/cursos/**",
            "/ocorrencias/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, FiltroDeAutenticacao filtro) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        auth -> auth.anyRequest().permitAll()
//                );
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(secao -> secao.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(ENDPOINTS_QUE_NAO_REQUEREM_AUTENTICACAO).permitAll()
//                                .requestMatchers(ENDPOINTS_DA_AQV).hasAnyRole("AQV","ADMIN")
                                .anyRequest().permitAll()
                )
                .addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
