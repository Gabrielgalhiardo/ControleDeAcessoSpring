package com.senai.controle_de_acesso_spring.application.dto.curso;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.ProfessorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Curso;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

public record UnidadeCurricularDto(
        Long id,
        String nome,
        Integer cargaHorariaTotal,
        List<ProfessorDto>professores
) {
    public static UnidadeCurricularDto toDTO(UnidadeCurricular u) {
        return new UnidadeCurricularDto(
                u.getId(),
                u.getNome(),
                u.getCargaHorariaTotal(),
                u.getProfessores().stream().map(ProfessorDto::toDTO).toList()
        );
    }

    public UnidadeCurricular fromDTO(Curso curso) {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(id);
        unidadeCurricular.setNome(nome);
        unidadeCurricular.setCargaHorariaTotal(cargaHorariaTotal);
        unidadeCurricular.setCurso(curso);
        unidadeCurricular.setProfessores(professores.stream().map(ProfessorDto::fromDTO).toList());
        return unidadeCurricular;
    }

    public UnidadeCurricular fromDTO() {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(id);
        unidadeCurricular.setNome(nome);
        unidadeCurricular.setCargaHorariaTotal(cargaHorariaTotal);
        unidadeCurricular.setProfessores(professores.stream().map(ProfessorDto::fromDTO).toList());
        return unidadeCurricular;
    }
}
