package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SemestreRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioSemanalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioSemanalService {
    @Autowired
    private HorarioSemanalRepository horarioSemanalRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    public void cadastrarHorariosSemanais(HorarioSemanalDTO horarioSemanalDTO){
        Semestre semestre = semestreRepository.findById(horarioSemanalDTO.semestreId())
                .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado"));

        List<HorarioSemanal> listaHorariosSemanais = semestre.getHorariosSemanais();

        horarioSemanalRepository.saveAll(listaHorariosSemanais);
    }
}
