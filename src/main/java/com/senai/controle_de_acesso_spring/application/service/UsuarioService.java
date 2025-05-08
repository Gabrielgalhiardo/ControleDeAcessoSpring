package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.UsuarioDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.AQV;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Coordenador;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeUsuario.*;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    public void cadastrarUsuario(UsuarioDto dto) {
        Usuario usuario;

        switch (dto.tipoDeUsuario()) {
            case ALUNO -> usuario = new Aluno();
            case AQV -> usuario = new AQV();
            case COORDENADOR -> usuario = new Coordenador();
            case PROFESSOR -> usuario = new Professor();
            default -> throw new IllegalArgumentException("Tipo de usuário inválido");
        }
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setIdAcesso("");
        usuario.setSenha("");

        usuarioRepository.save(usuario);
    }
}