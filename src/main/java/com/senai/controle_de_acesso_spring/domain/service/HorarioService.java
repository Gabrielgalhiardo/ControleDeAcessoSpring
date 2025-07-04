package com.senai.controle_de_acesso_spring.domain.service;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Ambiente;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.*;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.enums.DiaDaSemana;
import com.senai.controle_de_acesso_spring.domain.repository.curso.AmbienteRepository;
import com.senai.controle_de_acesso_spring.domain.repository.curso.UnidadeCurricularRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private AmbienteRepository ambienteRepository;

    public HorarioPadrao criarHorarioPadraoVazio(Semestre semestre) {
        return criarHorarioVazio(new HorarioPadrao(), semestre, null);
    }

    public HorarioSemanal criarHorarioSemanalVazio(Semestre semestre, LocalDate segundaFeira) {
        return criarHorarioVazio(new HorarioSemanal(), semestre, segundaFeira);
    }

    public <T extends HorarioBase> T criarHorarioVazio(T horario, Semestre semestre, LocalDate dataInicioSemana) {
        horario.setSemestre(semestre);
        if (horario instanceof HorarioSemanal semanal) {
            semanal.setSemanaReferencia(dataInicioSemana);
        }

        int aulasPorDia = horario.getSemestre().getSubTurma().getTurma().getQtdAulasPorDia();

        List<AulasDoDia> dias = new ArrayList<>();

        for (DiaDaSemana dia : DiaDaSemana.values()) {
            AulasDoDia diaObj = new AulasDoDia();
            diaObj.setDiaDaSemana(dia);
            diaObj.setHorario(horario);

            List<Aula> aulas = new ArrayList<>();
            for (int i = 0; i < aulasPorDia; i++) {
                Aula aula = new Aula();
                aula.setOrdem(i);
                aula.setAulasDia(diaObj);
                aulas.add(aula);
            }
            diaObj.setAulas(aulas);
            dias.add(diaObj);
        }

        horario.setListaDeAulasDoDia(dias);
        return horario;
    }


//    @Transactional
//    public void preencherHorario(HorarioBase horario, List<AulasDoDiaDTO> aulasDoDiaDTO) {
//
//        for (int i = 0; i < aulasDoDiaDTO.size(); i++) {
//            AulasDoDiaDTO aulasDoDiaDTOAtualizada = aulasDoDiaDTO.get(i);
//            AulasDoDia aulasDoDiaExistente = horario.getListaDeAulasDoDia().get(i);
//
//            aulasDoDiaExistente.setDiaDaSemana(aulasDoDiaDTOAtualizada.diaDaSemana());
//
//            List<Aula> aulasExistentes = aulasDoDiaExistente.getAulas();
//
//            for (int j = 0; j < aulasDoDiaDTOAtualizada.aulas().size(); j++) {
//                AulaDTO aulaDTO = aulasDoDiaDTOAtualizada.aulas().get(j);
//                Aula aulaExistente = aulasExistentes.get(j);
//
//                aulaExistente.setOrdem(j);
//                aulaExistente.setAulasDia(aulasDoDiaExistente);
//
//                if (aulaDTO.unidadeCurricularId() != null) {
//                    unidadeCurricularRepository.findById(aulaDTO.unidadeCurricularId())
//                            .ifPresent(aulaExistente::setUnidadeCurricular);
//                } else {
//                    aulaExistente.setUnidadeCurricular(null);
//                }
//
//                if (aulaDTO.professorId() != null) {
//                    professorRepository.findById(aulaDTO.professorId())
//                            .ifPresent(aulaExistente::setProfessor);
//                } else {
//                    aulaExistente.setProfessor(null);
//                }
//
//                if (aulaDTO.ambienteId() != null) {
//                    ambienteRepository.findById(aulaDTO.ambienteId())
//                            .ifPresent(aulaExistente::setAmbiente);
//                } else {
//                    aulaExistente.setAmbiente(null);
//                }
//            }
//        }
//    }

    @Transactional
    public void preencherHorario(HorarioBase horario, List<AulasDoDiaDTO> aulasDoDiaDTO) {
        // Coletar todos os IDs usados
        Set<Long> ucIds = new HashSet<>();
        Set<Long> profIds = new HashSet<>();
        Set<Long> ambienteIds = new HashSet<>();

        for (AulasDoDiaDTO dia : aulasDoDiaDTO) {
            for (AulaDTO aula : dia.aulas()) {
                if (aula.unidadeCurricularId() != null) ucIds.add(aula.unidadeCurricularId());
                if (aula.professorId() != null) profIds.add(aula.professorId());
                if (aula.ambienteId() != null) ambienteIds.add(aula.ambienteId());
            }
        }

        // Buscar todas as entidades de uma vez
        Map<Long, UnidadeCurricular> unidades = unidadeCurricularRepository.findAllById(ucIds)
                .stream().collect(Collectors.toMap(UnidadeCurricular::getId, uc -> uc));

        Map<Long, Professor> professores = professorRepository.findAllByIdIn(profIds)
                .stream().collect(Collectors.toMap(Professor::getId, p -> p));

        Map<Long, Ambiente> ambientes = ambienteRepository.findAllById(ambienteIds)
                .stream().collect(Collectors.toMap(Ambiente::getId, a -> a));

        // Atualizar os dados
        for (int i = 0; i < aulasDoDiaDTO.size(); i++) {
            AulasDoDiaDTO aulasDoDiaDTOAtualizada = aulasDoDiaDTO.get(i);
            AulasDoDia aulasDoDiaExistente = horario.getListaDeAulasDoDia().get(i);

            aulasDoDiaExistente.setDiaDaSemana(aulasDoDiaDTOAtualizada.diaDaSemana());

            List<Aula> aulasExistentes = aulasDoDiaExistente.getAulas();

            for (int j = 0; j < aulasDoDiaDTOAtualizada.aulas().size(); j++) {
                AulaDTO aulaDTO = aulasDoDiaDTOAtualizada.aulas().get(j);
                Aula aulaExistente = aulasExistentes.get(j);

                aulaExistente.setOrdem(j);
                aulaExistente.setAulasDia(aulasDoDiaExistente);

                // Setar entidades com os maps carregados
                aulaExistente.setUnidadeCurricular(
                        aulaDTO.unidadeCurricularId() != null
                                ? unidades.get(aulaDTO.unidadeCurricularId())
                                : null
                );

                aulaExistente.setProfessor(
                        aulaDTO.professorId() != null
                                ? professores.get(aulaDTO.professorId())
                                : null
                );

                aulaExistente.setAmbiente(
                        aulaDTO.ambienteId() != null
                                ? ambientes.get(aulaDTO.ambienteId())
                                : null
                );
            }
        }
    }

}
