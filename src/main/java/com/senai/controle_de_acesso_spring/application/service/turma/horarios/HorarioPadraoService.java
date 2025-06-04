package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioBaseDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioPadraoDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioBase;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SemestreRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioPadraoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HorarioPadraoService {

    @Autowired
    private HorarioPadraoRepository repository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private HorarioBaseService horarioBaseService;

    @Autowired
    private AulasDoDiaService aulasDoDiaService;

    @Transactional
    public HorarioPadrao salvarHorarioPadrao(Long semestreId, HorarioPadraoDTO horarioPadraoDTO) {
        Semestre semestre = semestreRepository.findById(semestreId)
                .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado"));
        List<AulasDoDia> aulasDoDia = aulasDoDiaService.adicionarAulaDoDia(horarioPadraoDTO.listaDeAulasDoDia().stream()
                .map(AulasDoDiaDTO::fromDTO).toList());

        System.out.println();

        HorarioPadrao horario = semestre.getHorarioPadrao();
        horario.setSemestre(semestre);
        horario.setListaDeAulasDoDia(aulasDoDia);
        repository.save(horario);
        horarioBaseService.preencherHorario(horario, aulasDoDia.stream()
                .map(AulasDoDiaDTO::toDTO).toList());

        return horario;
    }

    @Transactional
    public HorarioPadrao criarHorarioPadraoVazio(Semestre semestre, List<AulasDoDiaDTO> aulasDoDiaDTOS){
        HorarioPadrao horarioPadrao = new HorarioPadrao();
        horarioPadrao.setSemestre(semestre);
        horarioPadrao.setListaDeAulasDoDia(aulasDoDiaDTOS.stream()
                .map(AulasDoDiaDTO::fromDTO).collect(Collectors.toList()));
        repository.save(horarioPadrao);
        return horarioPadrao;
    }

    public List<HorarioPadraoDTO> listar() {
        return repository.findAll().stream()
                .map(HorarioPadraoDTO::toDTO).toList();
    }

    public Optional<HorarioPadraoDTO> buscarPorId(Long id) {
        return repository.findById(id).map(HorarioPadraoDTO::toDTO);
    }

    @Transactional
    public boolean atualizar(Long id, HorarioPadraoDTO dto) {
        Optional<HorarioPadrao> optional = repository.findById(id);
        if (optional.isEmpty()) return false;

        HorarioPadrao horario = optional.get();
        horarioBaseService.preencherHorario(horario, dto.listaDeAulasDoDia());
        repository.save(horario);
        return true;
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
