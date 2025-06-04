package com.senai.controle_de_acesso_spring.application.dto.turma.horario;

import com.senai.controle_de_acesso_spring.application.dto.turma.SemestreDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;

import java.time.LocalDate;
import java.util.List;

public record HorarioSemanalDTO(
        Long id,
        Long semestreId,
        LocalDate semanaReferencia,
        List<AulasDoDiaDTO> listaDeAulasDoDia
) {
    public static HorarioSemanalDTO toDTO(HorarioSemanal h) {
        return new HorarioSemanalDTO(
                h.getId(),
                h.getSemestre() != null ? h.getSemestre().getId() : null,
                h.getSemanaReferencia(),
                h.getListaDeAulasDoDia().stream().map(AulasDoDiaDTO::toDTO).toList()
        );
    }
    public HorarioSemanal fromDTO() {
        HorarioSemanal horario = new HorarioSemanal();
        horario.setSemanaReferencia(semanaReferencia);
        horario.setListaDeAulasDoDia(
                listaDeAulasDoDia.stream().map(AulasDoDiaDTO::fromDTO).toList()
        );
        return horario;
    }
}
