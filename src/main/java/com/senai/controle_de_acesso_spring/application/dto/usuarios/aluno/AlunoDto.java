package com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Justificativa;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.time.LocalDate;
import java.util.ArrayList;

public record AlunoDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email
){
    public static AlunoDto toDTO(Aluno a){
        return new AlunoDto(a.getId(), a.getNome(), a.getCpf(), a.getDataNascimento(), a.getEmail());
    }

    public Aluno fromDTO(){
        Aluno a = new Aluno();
        a.setId(id);
        a.setNome(nome);
        a.setCpf(cpf);
        a.setEmail(email);
        a.setDataNascimento(dataNascimento);
        a.setStatusDoUsuario(StatusDoUsuario.ATIVO);
        a.setIdAcesso("");
        a.setSenha("");
        a.setJustificativas(new ArrayList<Justificativa>());
        a.setOcorrencias(new ArrayList<Ocorrencia>());
        a.setSubTurmas(new ArrayList<SubTurma>());

        return a;
    }
}