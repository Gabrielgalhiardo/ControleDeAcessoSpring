package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.domain.model.entity.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.senai.controle_de_acesso_spring.domain.model.enums.NivelUsuario.*;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;


public void cadastrarUsuario(UsuarioDto dto) {
    Usuario usuario;

    switch (dto.tipoDeUsuario()) {
        case ALUNO -> usuario = new Aluno();
        case AQV -> usuario = new AQV();
        case COORDENADOR -> new Coordenador();
        case PROFESSOR -> new Professor();
        default -> throw new IllegalArgumentException("Tipo de usuário inválido");

        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setIdAcesso("");
        usuario.setSenha("");

        usuarioRepository.save(usuario);
    }
}
}