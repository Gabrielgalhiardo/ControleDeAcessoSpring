package com.senai.controle_de_acesso_spring.infrastructure.websocket;

import com.senai.controle_de_acesso_spring.infrastructure.util.JwtAuthHandshakeInterceptor;
import com.senai.controle_de_acesso_spring.infrastructure.util.JwtUtil;
import com.senai.controle_de_acesso_spring.infrastructure.util.UsernamePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setUserDestinationPrefix("/user");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new JwtAuthHandshakeInterceptor(jwtUtil))
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        String username = (String) attributes.get("username");
                        System.out.println("Username from WebSocket handshake: " + username);
                        return new UsernamePrincipal(username);
                    }
                })
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
