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
import com.senai.controle_de_acesso_spring.domain.service.HorarioService;
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

    @Autowired
    private HorarioService horarioService;

//    @Transactional
//    public HorarioPadrao salvarHorarioPadrao(Long semestreId, HorarioPadraoDTO horarioPadraoDTO) {
//        Semestre semestre = semestreRepository.findById(semestreId)
//                .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado"));
//        List<AulasDoDia> aulasDoDia = aulasDoDiaService.adicionarAulaDoDia(horarioPadraoDTO.listaDeAulasDoDia());
//
//        System.out.println();
//
//        HorarioPadrao horario = new HorarioPadrao();
//        horario.setSemestre(semestre);
//        horario.setListaDeAulasDoDia(aulasDoDia);
//        repository.save(horario);
//        horarioBaseService.preencherHorario(horario, aulasDoDia);
//
//        return horario;
//    }

    @Transactional
    public void salvarHorarioPadrao(Long semestreId, HorarioPadraoDTO dto) {
        Semestre semestre = semestreRepository.findById(semestreId)
                .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado"));

        HorarioPadrao horario = semestre.getHorarioPadrao();

        horarioService.preencherHorario(horario, dto.listaDeAulasDoDia());
        repository.save(horario);
    }

    @Transactional
    public HorarioPadrao criarHorarioPadraoVazio(Semestre semestre){
        HorarioPadrao horarioPadrao = new HorarioPadrao();
        horarioPadrao.setSemestre(semestre);
        horarioPadrao.setListaDeAulasDoDia(new ArrayList<>());
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
        List<AulasDoDia> aulasDoDiaList = dto.listaDeAulasDoDia().stream()
                        .map(a -> aulasDoDiaService.toEntity(a)).toList();
        horarioBaseService.preencherHorario(horario, aulasDoDiaList);
        repository.save(horario);
        return true;
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
