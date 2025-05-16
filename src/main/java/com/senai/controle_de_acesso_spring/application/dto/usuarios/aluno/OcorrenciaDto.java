package com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeOcorrencia;

import java.time.LocalDateTime;

public record OcorrenciaDto(
    Long id,
    TipoDeOcorrencia tipo,
    String descricao,
    StatusDaOcorrencia statusDaOcorrencia,
    LocalDateTime dataHoraCriacao,
    LocalDateTime dataHoraConclusao,

    Aluno aluno,
    Professor professor,
    UnidadeCurricular unidadeCurricular

) {
    public static OcorrenciaDto toDTO(Ocorrencia o){
        return new OcorrenciaDto(
                o.getId(),
                o.getTipo(),
                o.getDescricao(),
                o.getStatusDaOcorrencia(),
                o.getDataHoraCriacao(),
                o.getDataHoraConclusao(),
                o.getAluno(),
                o.getProfessorResponsavel(),
                o.getUnidadeCurricular());
    }

    public Ocorrencia fromDTO(){
        Ocorrencia o = new Ocorrencia();
        o.setId(id);
        o.setTipo(tipo);
        o.setDescricao(descricao);
        o.setStatusDaOcorrencia(statusDaOcorrencia);
        o.setDataHoraCriacao(dataHoraCriacao);
        o.setDataHoraConclusao(dataHoraConclusao);
        o.setAluno(aluno);
        o.setProfessorResponsavel(professor);
        o.setUnidadeCurricular(unidadeCurricular);

        return o;
    }
}
