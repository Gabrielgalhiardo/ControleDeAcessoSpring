package com.senai.controle_de_acesso_spring.application.dto.turma.horario;

import com.senai.controle_de_acesso_spring.application.dto.turma.SemestreDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioBase;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeHorario;

import java.util.List;

public record HorarioBaseDTO(
        Long id,
        TipoDeHorario tipoDeHorario,
        SemestreDTO semestreDTO,
        List<AulasDoDiaDTO> listaDeAulasDoDia
) {
    public static HorarioBaseDTO toDTO(HorarioBase h) {
        TipoDeHorario tipo = h instanceof HorarioPadrao ? TipoDeHorario.PADRAO : TipoDeHorario.SEMANAL;
        return new HorarioBaseDTO(
                h.getId(),
                tipo,
                SemestreDTO.toDTO(h.getSemestre()),
                h.getListaDeAulasDoDia().stream().map(AulasDoDiaDTO::toDTO).toList()
        );
    }
    public static HorarioBase fromDTO(HorarioBaseDTO horarioBaseDTO) {
        HorarioBase horarioBase;

        switch (horarioBaseDTO.tipoDeHorario()) {
            case TipoDeHorario.PADRAO -> horarioBase = new HorarioPadrao();
            case TipoDeHorario.SEMANAL -> horarioBase = new HorarioSemanal();
            default -> throw new IllegalArgumentException("Tipo de HorarioBase desconhecido: " + horarioBaseDTO.tipoDeHorario());
        }
        horarioBase.setId(horarioBaseDTO.id());
        horarioBase.setSemestre(SemestreDTO.fromDTO(horarioBaseDTO.semestreDTO()));
        horarioBase.setListaDeAulasDoDia(
                horarioBaseDTO.listaDeAulasDoDia().stream().map(AulasDoDiaDTO::fromDTO).toList()
        );

        return horarioBase;
    }

}
