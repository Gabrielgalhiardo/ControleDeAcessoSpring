package com.senai.controle_de_acesso_spring.application.dto.turma.horario;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;

public record AulaDTO(
        Long id,
        Integer ordem,
        Long unidadeCurricularId,
        Long professorId,
        Long ambienteId
) {
    public static AulaDTO toDTO(Aula a) {
        return new AulaDTO(
                a.getId(),
                a.getOrdem(),
                a.getUnidadeCurricular() != null ? a.getUnidadeCurricular().getId() : null,
                a.getProfessor() != null ? a.getProfessor().getId() : null,
                a.getAmbiente() != null ? a.getAmbiente().getId() : null
        );

    }

    public Aula fromDTO() {
        Aula aula = new Aula();
        return aula;
    }

}