package com.senai.controle_de_acesso_spring.application.dto.users;

import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeUsuario;

import java.time.LocalDate;

public record UsuarioDto(long id, String nome, String cpf, String email, LocalDate dataNascimento, TipoDeUsuario tipoDeUsuario) {
}
