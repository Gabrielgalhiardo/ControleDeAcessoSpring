package com.senai.controle_de_acesso_spring.application.dto.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.util.Date;
import java.util.List;

public record AlunoDto(String nome, String email, String telefoneCelular, String telefoneFixo,
                       String cpf, String senha, StatusDoUsuario statusDoUsuario, Date dataDeNascimento,
                       int idade, List<SubTurma> subTurmas) {
}