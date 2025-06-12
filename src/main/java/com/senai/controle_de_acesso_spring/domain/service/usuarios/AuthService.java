package com.senai.controle_de_acesso_spring.domain.service.usuarios;


import com.senai.controle_de_acesso_spring.application.dto.auth.LoginDTO;
import com.senai.controle_de_acesso_spring.application.dto.auth.RecuperarTokenJwtDTO;
import com.senai.controle_de_acesso_spring.application.dto.auth.RegisterDTO;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.UsuarioDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.*;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.PermissoesDoUsuario;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeUsuario;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.UsuarioRepository;
import com.senai.controle_de_acesso_spring.infrastructure.security.authentication.UserDetailsImpl;
import com.senai.controle_de_acesso_spring.infrastructure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RecuperarTokenJwtDTO authenticarUsuario(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecuperarTokenJwtDTO(
                jwtUtil.generateToken(userDetails.getUsuario().getId(),
                        userDetails.getUsername(),
                        recuperarTipoDeUsuario(userDetails.getUsuario()).name()));
    }

    public void registrarUsuarioSimples(UsuarioDto usuarioDto){
        Usuario usuario = switch (usuarioDto.tipoDeUsuario()) {
            case ALUNO -> new Aluno();
            case PROFESSOR -> new Professor();
            case ADMINISTRADOR -> new Admin();
            case COORDENADOR -> new Coordenador();
            case AQV -> new AQV();
        };
        usuario.setEmail(usuarioDto.email());
        usuario.setCpf(usuarioDto.cpf());
        usuario.setPermissoes(new ArrayList<>());
        usuario.getPermissoes().add(pegarPermissaoPorTipoDeUsuario(usuarioDto.tipoDeUsuario()));
        usuario.setNome(usuarioDto.nome());
        usuario.setSenha(passwordEncoder.encode(usuarioDto.senha()));
        usuario.setStatusDoUsuario(StatusDoUsuario.ATIVO);

        usuarioRepository.save(usuario);
    }

    public UsuarioDto cadastrarUsuario(UsuarioDto dto) {
        Usuario usuario = instanciaUsuario(dto);
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setStatusDoUsuario(StatusDoUsuario.ATIVO);
        usuario.setCpf(dto.cpf());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPermissoes(new ArrayList<>());
        usuario.getPermissoes().add(pegarPermissaoPorTipoDeUsuario(dto.tipoDeUsuario()));
        usuario.setIdAcesso(dto.idAcesso());
        usuarioRepository.save(usuario);
        return UsuarioDto.toDTO(usuario);
    }

    private TipoDeUsuario recuperarTipoDeUsuario(Usuario usuario) {
        return switch (usuario) {
            case Aluno aluno -> TipoDeUsuario.ALUNO;
            case Professor professor -> TipoDeUsuario.PROFESSOR;
            case AQV aqv -> TipoDeUsuario.AQV;
            case Coordenador coordenador -> TipoDeUsuario.COORDENADOR;
            case Admin admin -> TipoDeUsuario.ADMINISTRADOR;
            default -> throw new IllegalArgumentException("Tipo de usuário desconhecido: " + usuario.getClass().getSimpleName());
        };
    }

    private Usuario instanciaUsuario(UsuarioDto dto) {
        return switch (dto.tipoDeUsuario()) {
            case ALUNO -> new Aluno();
            case PROFESSOR -> new Professor();
            case ADMINISTRADOR -> new Admin();
            case COORDENADOR -> new Coordenador();
            case AQV -> new AQV();
        };
    }

    private PermissoesDoUsuario pegarPermissaoPorTipoDeUsuario(TipoDeUsuario tipoDeUsuario) {
        return switch (tipoDeUsuario) {
            case ALUNO -> PermissoesDoUsuario.ALUNO;
            case PROFESSOR -> PermissoesDoUsuario.PROFESSOR;
            case ADMINISTRADOR -> PermissoesDoUsuario.ADMINISTRADOR;
            case COORDENADOR -> PermissoesDoUsuario.COORDENADOR;
            case AQV -> PermissoesDoUsuario.AQV;
        };
    }
}
