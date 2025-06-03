package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioBaseDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioBase;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioBaseService {

    @Autowired
    private HorarioBaseRepository horarioBaseRepository;

//    public void cadastrarHorarioBase(HorarioBaseDTO horarioBaseDTO) {
//        HorarioBase horarioBase = HorarioBaseDTO.toDTO(horarioBaseDTO);
//        horarioBaseRepository.save(horarioBase);
//    }

    public void preencherHorario(HorarioBase horarioBase, List<AulasDoDiaDTO> aulasDoDia) {
        System.out.println("Horario base: "+horarioBase.getSemestre().toString());
        if(horarioBase instanceof HorarioPadrao horarioPadrao){
            horarioPadrao.setSemestre(horarioBase.getSemestre());
            horarioPadrao.setListaDeAulasDoDia(aulasDoDia.stream()
                    .map(AulasDoDiaDTO::fromDTO)
                    .toList());
        } else if (horarioBase instanceof  HorarioSemanal horarioSemanal) {
            horarioSemanal.setSemestre(horarioBase.getSemestre());
            horarioSemanal.setSemanaReferencia(((HorarioSemanal) horarioBase).getSemanaReferencia());
            horarioSemanal.setListaDeAulasDoDia(aulasDoDia.stream()
                    .map(AulasDoDiaDTO::fromDTO)
                    .toList());
        }
//        horarioBase.setListaDeAulasDoDia(
//                aulasDoDia.stream().map(AulasDoDiaDTO::fromDTO).toList()
//        );
        horarioBaseRepository.save(horarioBase);

    }
}
