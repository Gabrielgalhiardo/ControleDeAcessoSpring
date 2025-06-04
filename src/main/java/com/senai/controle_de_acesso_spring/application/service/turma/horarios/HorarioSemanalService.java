package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
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

    @Autowired
    private HorarioBaseService horarioBaseService;

    @Autowired
    private AulasDoDiaService aulasDoDiaService;

    public List<HorarioSemanalDTO> cadastrarHorariosSemanais(List<HorarioSemanalDTO> horarioSemanalDTOS){
        Semestre SemestreEncontrado = semestreRepository.findById(horarioSemanalDTOS.getLast().semestreId())
                .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado"));



        List<HorarioSemanal> listaHorariosSemanais = horarioSemanalDTOS.stream()
                .map(HorarioSemanalDTO::fromDTO)
                .toList();
        listaHorariosSemanais.forEach(h -> h.setSemestre(SemestreEncontrado));


        List<AulasDoDiaDTO> listaAulasDoDia = listaHorariosSemanais.stream()
                .flatMap(h -> h.getListaDeAulasDoDia().stream())
                .map(AulasDoDiaDTO::toDTO)
                .toList();
        listaHorariosSemanais.forEach(h -> horarioBaseService.preencherHorario(h, listaAulasDoDia));

        horarioSemanalRepository.saveAll(listaHorariosSemanais);
        return listaHorariosSemanais.stream()
                .map(HorarioSemanalDTO::toDTO)
                .toList();
    }
}
