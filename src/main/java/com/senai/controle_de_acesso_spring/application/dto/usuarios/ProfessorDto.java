package com.senai.controle_de_acesso_spring.application.dto.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.time.LocalDate;

public record ProfessorDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email
) {
    public static ProfessorDto toDTO(Professor p){
        return new ProfessorDto(p.getId(), p.getNome(), p.getCpf(), p.getDataNascimento(), p.getEmail());
    }

    public Professor fromDTO(){
        Professor professor = new Professor();
        professor.setId(id);
        professor.setNome(nome);
        professor.setCpf(cpf);
        professor.setEmail(email);
        professor.setDataNascimento(dataNascimento);
        professor.setStatusDoUsuario(StatusDoUsuario.ATIVO);
        return professor;
    }
}
