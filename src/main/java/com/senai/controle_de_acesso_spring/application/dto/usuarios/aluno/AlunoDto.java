package com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Justificativa;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.time.LocalDate;
import java.util.List;

public record AlunoDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String idAcesso,
        String senha,
        List<Justificativa> justificativas,
        List<Ocorrencia> ocorrencias,
        SubTurma subTurmas
){
    public static AlunoDto toDTO(Aluno a){
        return new AlunoDto(a.getId(), a.getNome(), a.getCpf(), a.getDataNascimento(), a.getEmail(),
                a.getIdAcesso(), a.getSenha(), a.getJustificativas(), a.getOcorrencias(), a.getSubTurma());
    }

    public Aluno fromDTO(){
        Aluno a = new Aluno();
        a.setId(id);
        a.setNome(nome);
        a.setCpf(cpf);
        a.setEmail(email);
        a.setDataNascimento(dataNascimento);
        a.setStatusDoUsuario(StatusDoUsuario.ATIVO);
        a.setIdAcesso(idAcesso);
        a.setSenha(senha);
        a.setJustificativas(justificativas);
        a.setOcorrencias(ocorrencias);
        a.setSubTurma(subTurmas);

        return a;
    }
}