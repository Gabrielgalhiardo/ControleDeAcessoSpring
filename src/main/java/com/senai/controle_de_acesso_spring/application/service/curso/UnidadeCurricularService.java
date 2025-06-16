package com.senai.controle_de_acesso_spring.application.service.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.UnidadeCurricularDto;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.ProfessorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.application.dto.curso.unidade_curricular.UnidadeCurricularProfessorDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioBase;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.curso.UnidadeCurricularRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnidadeCurricularService {
    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;
    @Autowired
    private AlunoRepository alunoRepository;

//    public void cadastrarUnidadeCurricular(UnidadeCurricularDto unidadeCurricularDto) {
//        unidadeCurricularRepository.save(unidadeCurricularDto.);
//    }

    public List<UnidadeCurricularDto> listarUnidadeCurricular() {
        return unidadeCurricularRepository.findAll()
                .stream().map(UnidadeCurricularDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UnidadeCurricularDto> buscarPorId(Long id) {
        return unidadeCurricularRepository.findById(id).map(uc -> new UnidadeCurricularDto(
                uc.getId(),
                uc.getNome(),
                uc.getCargaHorariaTotal(),
                uc.getProfessores().stream()
                        .map(ProfessorDto::toDTO)
                        .collect(Collectors.toList())
        ));
    }

    public boolean atualizarUnidadeCurricular(Long id, UnidadeCurricularDto unidadeCurricularDto) {
        return unidadeCurricularRepository.findById(id).map(unidadeCurricular -> {
            UnidadeCurricular unidadeCurricularAtualizado = new UnidadeCurricular();
            unidadeCurricular.setId(unidadeCurricularAtualizado.getId());
            unidadeCurricular.setNome(unidadeCurricular.getNome());
            unidadeCurricularRepository.save(unidadeCurricular);
            return true;
        }).orElse(false);
    }

    public Optional<UnidadeCurricular> buscarUnidadeCurricularPeloIdDoProfessor(Long professorId) {
        return unidadeCurricularRepository.findByProfessorId(professorId);
    }

    public List<UnidadeCurricularProfessorDTO> listarUnidadesCurricularesDoAluno(Long alunoId) {
        LocalDate hoje = LocalDate.now();
        DayOfWeek diaAtual = hoje.getDayOfWeek();

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o ID: " + alunoId));

        SubTurma subTurma = aluno.getSubTurma();

        if (subTurma == null) {
            throw new IllegalStateException("Aluno não está vinculado a nenhuma SubTurma.");
        }

        List<Semestre> semestres = subTurma.getSemestres();

        for (Semestre semestre : semestres) {
            HorarioSemanal horarioSemanalAtual = semestre.getHorariosSemanais().stream()
                    .filter(hs -> hs.getSemanaReferencia() != null &&
                            hs.getSemanaReferencia().isEqual(hoje.with(DayOfWeek.MONDAY)))
                    .findFirst()
                    .orElse(null);

            HorarioBase horario = (horarioSemanalAtual != null)
                    ? horarioSemanalAtual
                    : semestre.getHorarioPadrao();

            if (horario == null) continue;

            AulasDoDia aulasDoDia = horario.getListaDeAulasDoDia().stream()
                    .filter(ad -> ad.getDiaDaSemana() != null && ad.getDiaDaSemana().toDayOfWeek().equals(diaAtual))
                    .findFirst()
                    .orElse(null);

            if (aulasDoDia != null) {
                return aulasDoDia.getAulas().stream()
                        .map(aula -> new UnidadeCurricularProfessorDTO(
                                aula.getUnidadeCurricular().getId(),
                                aula.getUnidadeCurricular().getNome() == null ? null : aula.getUnidadeCurricular().getNome(),
                                aula.getProfessor().getNome() == null ? null : aula.getProfessor().getNome(),
                                aula.getProfessor().getId() == null ? null : aula.getProfessor().getId(),
                                aula.getUnidadeCurricular().getId() == null ? null : aula.getUnidadeCurricular().getId()
                        ))
                        .collect(Collectors.toList());
            }
        }

        return List.of();
    }

    public boolean inativarUnidadeCurricular(Long id) {
        if (unidadeCurricularRepository.existsById(id)){
            unidadeCurricularRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}