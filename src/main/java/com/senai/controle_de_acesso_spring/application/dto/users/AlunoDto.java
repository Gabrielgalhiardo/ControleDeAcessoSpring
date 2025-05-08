package com.senai.controle_de_acesso_spring.application.dto.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Justificativa;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record AlunoDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String idAcesso,
        String email)
{
}