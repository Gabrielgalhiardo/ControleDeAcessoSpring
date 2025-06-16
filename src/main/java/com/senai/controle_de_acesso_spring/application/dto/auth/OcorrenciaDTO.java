package com.senai.controle_de_acesso_spring.application.dto.auth;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeOcorrencia;

import java.time.LocalDateTime;

public record OcorrenciaDTO(
        Long id,
        TipoDeOcorrencia tipo,
        String descricao,
        Long alunoId,
        Long unidadeCurricularId,
        Long professorResponsavelId,
        StatusDaOcorrencia status,
        LocalDateTime dataHoraCriacao
) {
    public static OcorrenciaDTO toDTO(Ocorrencia ocorrencia) {
        return new OcorrenciaDTO(
                ocorrencia.getId(),
                ocorrencia.getTipo(),
                ocorrencia.getDescricao(),
                ocorrencia.getAluno() != null ? ocorrencia.getAluno().getId() : null,
                ocorrencia.getUnidadeCurricular() != null ? ocorrencia.getUnidadeCurricular().getId() : null,
                ocorrencia.getProfessorResponsavel() != null ? ocorrencia.getProfessorResponsavel().getId() : null,
                ocorrencia.getStatusDaOcorrencia(),
                ocorrencia.getDataHoraCriacao()
        );
    }

    public Ocorrencia fromDTO() {
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(id);
        ocorrencia.setTipo(tipo);
        ocorrencia.setDescricao(descricao);
        ocorrencia.setStatusDaOcorrencia(status);
        ocorrencia.setDataHoraCriacao(dataHoraCriacao);
        return ocorrencia;
    }
}
