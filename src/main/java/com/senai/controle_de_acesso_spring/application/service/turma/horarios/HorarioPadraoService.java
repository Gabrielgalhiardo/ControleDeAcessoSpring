package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioPadraoDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SemestreRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioPadraoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioPadraoService {

    @Autowired
    private HorarioPadraoRepository repository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Transactional
    public void salvarHorarioPadrao(Long semestreId, HorarioPadraoDTO dto) {
        Semestre semestre = semestreRepository.findById(semestreId)
                .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado"));

        HorarioPadrao horario = semestre.getHorarioPadrao();

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
        return true;
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
