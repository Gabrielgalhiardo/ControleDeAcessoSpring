package com.senai.controle_de_acesso_spring.application.dto.turma;

import com.senai.controle_de_acesso_spring.application.dto.curso.CursoDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Curso;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.model.enums.Periodo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public record TurmaDto(Long id,
                       String siglaDaTurma,
                       Periodo periodo,
                       LocalDate dataInicial,
                       LocalTime horarioEntrada,
                       Integer qtdSemestres,
                       Integer qtdAulasPorDia,
                       Curso curso,
                       List<SubTurmaDTO> subTurmas) {
    public static TurmaDto toDTO(Turma t){
        return new TurmaDto(t.getId(),
                t.getSiglaDaTurma(),
                t.getPeriodo(),
                t.getDataInicial(),
                t.getHorarioEntrada(),
                t.getQtdSemestres(),
                t.getQtdAulasPorDia(),
                t.getCurso(),
                t.getSubTurmas().stream()
                        .map(SubTurmaDTO::toDTO)
                        .toList());
    }

    public Turma fromDTO(){
        Turma t = new Turma();
        t.setId(id);
        t.setSiglaDaTurma(siglaDaTurma);
        t.setPeriodo(periodo);
        t.setDataInicial(dataInicial);
        t.setHorarioEntrada(horarioEntrada);
        t.setQtdSemestres(qtdSemestres);
        t.setQtdAulasPorDia(qtdAulasPorDia);
        t.setCurso(curso);
        List<SubTurma> subturmasConvertidas = subTurmas.stream()
                .map(SubTurmaDTO::fromDTO).collect(Collectors.toList());
        subturmasConvertidas.forEach(s -> s.setTurma(t));
        t.setSubTurmas(subturmasConvertidas);

        return t;
    }
}