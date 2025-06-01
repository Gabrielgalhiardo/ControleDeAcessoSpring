package com.senai.controle_de_acesso_spring.application.dto.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioSemanalRepository;

import java.util.List;

public record SemestreDTO (
        Long id,
        int numero,
        String nomeDaTurma,
        HorarioPadrao horarioPadrao,
        List<HorarioSemanalDTO> horarioSemanalDTOS,
        SubTurma subTurma
) {
    public static Semestre fromDTO(SemestreDTO semestreDTO) {
        Semestre semestre = new Semestre();
        semestre.setId(semestreDTO.id);
        semestre.setNumero(semestreDTO.numero);
        semestre.setNomeDaTurma(semestreDTO.nomeDaTurma);
        semestre.setHorarioPadrao(semestreDTO.horarioPadrao);
        semestre.setHorariosSemanais(semestreDTO.horarioSemanalDTOS.stream()
                .map(HorarioSemanalDTO::fromDTO)
                .toList());
        semestre.setSubTurma(semestreDTO.subTurma);
        return semestre;
    }
    public static SemestreDTO toDTO(Semestre semestre) {
        return new SemestreDTO(
                semestre.getId(),
                semestre.getNumero(),
                semestre.getNomeDaTurma(),
                semestre.getHorarioPadrao(),
                semestre.getHorariosSemanais().stream()
                        .map(HorarioSemanalDTO::toDTO)
                        .toList(),
                semestre.getSubTurma()
        );
    }
}
