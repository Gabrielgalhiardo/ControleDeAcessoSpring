package com.senai.controle_de_acesso_spring.application.dto.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.*;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeUsuario;

import java.time.LocalDate;

public record UsuarioDto(Long id,
                         String nome,
                         String cpf,
                         String email,
                         LocalDate dataNascimento,
                         TipoDeUsuario tipoDeUsuario) {
    public static UsuarioDto toDTO(Usuario u) {
        TipoDeUsuario tipo = switch (u) {
            case Aluno a -> TipoDeUsuario.ALUNO;
            case Professor p -> TipoDeUsuario.PROFESSOR;
            case Coordenador c -> TipoDeUsuario.COORDENADOR;
            case AQV aqv -> TipoDeUsuario.AQV;
            default -> throw new IllegalArgumentException("Tipo de usuário desconhecido");
        };
        return new UsuarioDto(u.getId(), u.getNome(), u.getCpf(), u.getEmail(), u.getDataNascimento(), tipo);
    }

    public Usuario fromDTO() {
        Usuario usuario = switch (tipoDeUsuario) {
            case ALUNO -> new Aluno();
            case PROFESSOR -> new Professor();
            case ADMINISTRADOR -> new Admin();
            case COORDENADOR -> new Coordenador();
            case AQV -> new AQV();
        };
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setDataNascimento(dataNascimento);
        usuario.setStatusDoUsuario(StatusDoUsuario.ATIVO);
        usuario.setIdAcesso("");
        usuario.setSenha("");
        return usuario;
    }
}
