package com.senai.controle_de_acesso_spring.application.dto.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeUsuario;

import java.time.LocalDate;
import java.util.List;

public record CoordenadorDto(
        Long id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        List<Professor> equipeProfessores,
        TipoDeUsuario tipoDeUsuario) {
}
