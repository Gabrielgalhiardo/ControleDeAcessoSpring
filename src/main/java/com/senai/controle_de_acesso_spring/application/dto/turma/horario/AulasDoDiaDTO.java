package com.senai.controle_de_acesso_spring.application.dto.turma.horario;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.enums.DiaDaSemana;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record AulasDoDiaDTO(
        Long id,
        DiaDaSemana diaDaSemana,
        List<AulaDTO> aulas
) {
    public static AulasDoDiaDTO toDTO(AulasDoDia d) {
        return new AulasDoDiaDTO(
                d.getId(),
                d.getDiaDaSemana(),
                d.getAulas().stream().map(AulaDTO::toDTO).toList()
        );
    }

    public AulasDoDia fromDTO() {
        AulasDoDia dia = new AulasDoDia();
        dia.setDiaDaSemana(diaDaSemana);
//        dia.setAulas(aulas.stream().map(a -> a.fromDTO()).collect(Collectors.toList()));
        return dia;
    }
}