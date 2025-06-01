package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioBaseDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioBase;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioBaseService {
    @Autowired
    private HorarioBaseRepository horarioBaseRepository;

    @Autowired
    private HorarioPadraoService horarioPadraoService;

    @Autowired
    private HorarioPadraoService horarioSemanalService;

//    public void cadastrarHorarioBase(HorarioBaseDTO horarioBaseDTO) {
//        HorarioBase horarioBase = HorarioBaseDTO.toDTO(horarioBaseDTO);
//        horarioBaseRepository.save(horarioBase);
//    }

}
