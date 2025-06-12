package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioPadraoDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SemestreRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioSemanalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
//        listaHorariosSemanais.forEach(h -> horarioBaseService.preencherHorario(h, listaAulasDoDia));

        horarioSemanalRepository.saveAll(listaHorariosSemanais);
        return listaHorariosSemanais.stream()
                .map(HorarioSemanalDTO::toDTO)
                .toList();
    }

    public List<HorarioSemanalDTO> listar(){
        return horarioSemanalRepository.findAll().stream().map(HorarioSemanalDTO::toDTO).toList();
    }

    public Optional<HorarioSemanalDTO> buscarPorId(Long id){
        return horarioSemanalRepository.findById(id).map(HorarioSemanalDTO::toDTO);
    }

    @Transactional
    public boolean atualizar(Long id, HorarioSemanalDTO dto){
        Optional<HorarioSemanal> optional = horarioSemanalRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }

        HorarioSemanal horario = optional.get();
        List<AulasDoDia> aulasDoDiaList = dto.listaDeAulasDoDia().stream()
                .map(a -> aulasDoDiaService.toEntity(a)).toList();
        horarioBaseService.preencherHorario(horario, aulasDoDiaList);
        horarioSemanalRepository.save(horario);
        return true;
    }


    public boolean deletar(Long id){
        if (!horarioSemanalRepository.existsById(id)){
            return false;
        }
        horarioSemanalRepository.deleteById(id);
        return true;
    }
}
