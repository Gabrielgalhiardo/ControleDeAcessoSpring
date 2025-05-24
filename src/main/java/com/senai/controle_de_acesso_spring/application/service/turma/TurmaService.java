package com.senai.controle_de_acesso_spring.application.service.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.TurmaDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepo;

    @Transactional
    public void cadastrarTurma(TurmaDto turmaDto){
        turmaRepo.save(turmaDto.fromDTO());
    }

    public List<TurmaDto> listarTurmasAtivos(){
        return turmaRepo.findAll()
                .stream().map(TurmaDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TurmaDto> buscarPorId(Long id){
        return turmaRepo.findById(id)
                .map(TurmaDto::toDTO);
    }

    public boolean atualizarTurma(Long id, TurmaDto turmaDto){
        return turmaRepo.findById(id).map(turma -> {
            Turma turmaAtualizado = turmaDto.fromDTO();
            turma.setSiglaDaTurma(turmaAtualizado.getSiglaDaTurma());
            turma.setPeriodo(turmaAtualizado.getPeriodo());
            turma.setDataInicial(turmaAtualizado.getDataInicial());
            turma.setHorarioEntrada(turmaAtualizado.getHorarioEntrada());
            turma.setQtdSemestres(turmaAtualizado.getQtdSemestres());
            turma.setQtdAulasPorDia(turmaAtualizado.getQtdAulasPorDia());
            turmaRepo.save(turma);
            return true;
        }).orElse(false);
    }

    public boolean inativar(Long id){
        return turmaRepo.findById(id).map(turma -> {
            turmaRepo.delete(turma);
            return true;
        }).orElse(false);
    }
}
