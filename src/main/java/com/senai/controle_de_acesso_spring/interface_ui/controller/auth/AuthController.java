package com.senai.controle_de_acesso_spring.interface_ui.controller.auth;

import com.senai.controle_de_acesso_spring.application.dto.auth.LoginDTO;
import com.senai.controle_de_acesso_spring.application.dto.auth.RecuperarTokenJwtDTO;
import com.senai.controle_de_acesso_spring.application.dto.auth.RegisterDTO;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.UsuarioDto;
import com.senai.controle_de_acesso_spring.domain.service.usuarios.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RecuperarTokenJwtDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok().body(authService.authenticarUsuario(loginDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RecuperarTokenJwtDTO> registro(@RequestBody UsuarioDto usuarioDto) {
        try {
            authService.registrarUsuarioSimples(usuarioDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
