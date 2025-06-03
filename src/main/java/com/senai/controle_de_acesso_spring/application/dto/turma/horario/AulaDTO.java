package com.senai.controle_de_acesso_spring.application.dto.turma.horario;

import com.senai.controle_de_acesso_spring.application.dto.curso.AmbienteDto;
import com.senai.controle_de_acesso_spring.application.dto.curso.UnidadeCurricularDto;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.ProfessorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;

public record AulaDTO(
        Long id,
        Integer ordem,
        UnidadeCurricularDto unidadeCurricularDto,
        ProfessorDto professorDto,
        AmbienteDto ambienteDto,
        AulasDoDiaDTO aulasDoDiaDTO
) {
    public static AulaDTO toDTO(Aula a) {
        return new AulaDTO(
                a.getId(),
                a.getOrdem(),
                UnidadeCurricularDto.toDTO(a.getUnidadeCurricular()),
                ProfessorDto.toDTO(a.getProfessor()),
                AmbienteDto.toDTO(a.getAmbiente()),
                AulasDoDiaDTO.toDTO(a.getAulasDia())
        );

    }

    public Aula fromDTO() {
        Aula aula = new Aula();
        aula.setOrdem(ordem);
        aula.setUnidadeCurricular(unidadeCurricularDto.fromDTO());
        aula.setProfessor(professorDto.fromDTO());
        aula.setAmbiente(ambienteDto.fromDTO());
        aula.setAulasDia(aulasDoDiaDTO.fromDTO());
        return aula;
    }

}