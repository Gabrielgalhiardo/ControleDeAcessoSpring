package com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Justificativa;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaJustificativa;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeJustificativa;

import java.time.LocalDate;

public record JustificativaDto(
    Long id,
    TipoDeJustificativa tipoDeJustificativa,
    String descricao,
    String anexo,
    LocalDate dataInicial,
    Integer qtdDias,
    StatusDaJustificativa statusDaJustificativa,
    Aluno aluno
) {
    public static JustificativaDto toDTO(Justificativa j) {
        return new JustificativaDto(j.getId(), j.getTipoDeJustificativa(), j.getDescricao(), j.getAnexo(), j.getDataInicial(), j.getQtdDias(), j.getStatusDaJustificativa(), j.getAluno());
    }

    public Justificativa fromDTO() {
        Justificativa justificativa = new Justificativa();
        justificativa.setId(id);
        justificativa.setTipoDeJustificativa(tipoDeJustificativa);
        justificativa.setDescricao(descricao);
        justificativa.setAnexo(anexo);
        justificativa.setDataInicial(dataInicial);
        justificativa.setQtdDias(qtdDias);
        justificativa.setStatusDaJustificativa(statusDaJustificativa);
        justificativa.setAluno(aluno);
        return justificativa;
    }
}
