package com.senai.controle_de_acesso_spring.infrastructure.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class JwtAuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    public JwtAuthHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        String token = null;

        if (request instanceof ServletServerHttpRequest servletRequest) {
            String authorizationHeader = servletRequest.getServletRequest().getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                System.out.println("WebSocket handshake: Token found in header: " + token);
            }
        }

        if (token == null && request.getURI().toString().contains("token=")) {
            String uri = request.getURI().toString();
            token = uri.substring(uri.indexOf("token=") + 6);
            int endOfToken = token.indexOf("&");
            if (endOfToken != -1) {
                token = token.substring(0, endOfToken);
            }
        }

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                attributes.put("username", username);
                System.out.println("WebSocket handshake: Token VALID. Username: " + username);
                return true;
            } else {
                System.out.println("WebSocket handshake: Token INVALID or EXPIRED.");
                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return false;
            }
        } else {
            System.out.println("WebSocket handshake: No token found.");
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return false;
        }

    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        if (exception != null) {
            System.err.println("WebSocket handshake error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}