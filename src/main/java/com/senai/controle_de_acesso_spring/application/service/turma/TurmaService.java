package com.senai.controle_de_acesso_spring.application.service.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.SubTurmaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.TurmaDto;
import com.senai.controle_de_acesso_spring.application.service.curso.CursoService;
import com.senai.controle_de_acesso_spring.application.service.usuarios.aluno.AlunoService;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Curso;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.turma.TurmaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.senai.controle_de_acesso_spring.domain.repository.turma.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepo;

    @Autowired
    private SubTurmaService subTurmaService;

    @Autowired
    private CursoService cursoService;

//    @Transactional
//    public void cadastrarTurma(TurmaDto turmaDto){
//        turmaRepo.save(turmaDto.fromDTO());
//    }

    @Transactional
    public Turma salvarTurma(TurmaDto dto) {
        Turma turma = new Turma();
        turma.setSiglaDaTurma(dto.siglaDaTurma());
        turma.setPeriodo(dto.periodo());
        turma.setDataInicial(dto.dataInicial());
        turma.setHorarioEntrada(dto.horarioEntrada());
        turma.setQtdSemestres(dto.qtdSemestres());
        turma.setQtdAulasPorDia(dto.qtdAulasPorDia());

        Curso curso = cursoService.buscarPorId(dto.curso().getId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " ))
                .fromDTO();
        turma.setCurso(curso);



//        for(SubTurmaDTO subTurmaDTO : dto.subTurmas()) {
//            subTurmaService.criarSubTurma(subTurmaDTO);
//        }
//        turma.setSubTurmas(dto.subTurmas().stream()
//                .map(SubTurmaDTO::fromDTO).collect(Collectors.toList()));

        return turmaRepo.save(turma);
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
