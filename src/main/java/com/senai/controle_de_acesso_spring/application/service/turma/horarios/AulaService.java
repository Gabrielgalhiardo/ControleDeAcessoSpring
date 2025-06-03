package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulaDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeCurso;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    public Aula salvarAula(AulaDTO aulaDTO){
        return aulaRepository.save(aulaDTO.fromDTO());
    }


    public static Aula pegarAulaAtualPelaSubTurma(SubTurma subTurma) {
        LocalTime horarioEntrada = subTurma.getTurma().getHorarioEntrada();
        int minutosPorAula = subTurma.getTurma().getCurso().getTipoDeCurso().getMinutosPorAula();
        int minutosDeIntervalo = subTurma.getTurma().getCurso().getTipoDeCurso().getIntevarloMinutos();

        DayOfWeek hoje = DayOfWeek.from(LocalDate.now());

        Semestre semestre = subTurma.getSemestres().stream()
                .max(Comparator.comparing(Semestre::getNumero))
                .orElseThrow(() -> new RuntimeException("Semestre não encontrado."));



//        HorarioSemanal horarioSemana = (HorarioSemanal) semestre.getHorariosSemanais().stream().filter(horarioSemanal -> horarioSemanal.getSemanaReferencia().equals(semanaAtual));
//        HorarioPadrao horarioPadrao = semestre.getHorarioPadrao();
//        Optional<AulasDoDia> aulasDoDiaSemana = Optional.of(horarioSemana.getListaDeAulasDoDia().stream()
//                .filter(dia -> dia.getDiaDaSemana().equals(hoje))
//                .findFirst()
//                .orElse(aulasoDia -> {
//                             aulasDoDia = horarioPadrao.getListaDeAulasDoDia().stream()
//                                    .filter(dia -> dia.getDiaDaSemana().equals(hoje))
//                                    .findFirst()
//                                    .orElseThrow(() -> new RuntimeException("Sem aulas hoje."));
//                        }
//                );

        LocalDate semanaAtual = LocalDate.now();
        switch (semanaAtual.getDayOfWeek()){
            case DayOfWeek.TUESDAY -> semanaAtual.plusDays(-1);

            case DayOfWeek.WEDNESDAY -> semanaAtual.plusDays(-2);

            case DayOfWeek.THURSDAY -> semanaAtual.plusDays(-3);

            case DayOfWeek.FRIDAY -> semanaAtual.plusDays(-4);

        }
        LocalTime agora = LocalTime.now();

        Optional<AulasDoDia> aulasDoDiaOpt = Optional.empty();

        Optional<HorarioSemanal> horarioSemanaOpt = semestre.getHorariosSemanais().stream()
                .filter(horarioSemanal -> horarioSemanal.getSemanaReferencia().equals(semanaAtual))
                .findFirst();

        Optional<HorarioPadrao> horarioSemanaPadrao = Optional.ofNullable(semestre.getHorarioPadrao());

        if (horarioSemanaOpt.isPresent()) {
            aulasDoDiaOpt = Optional.ofNullable(horarioSemanaOpt.get().getListaDeAulasDoDia().stream()
                    .filter(dia -> dia.getDiaDaSemana().equals(hoje))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Sem aulas hoje.")));

        } else if (horarioSemanaPadrao.isPresent()) {
            aulasDoDiaOpt = Optional.ofNullable(horarioSemanaPadrao.get().getListaDeAulasDoDia().stream()
                    .filter(dia -> dia.getDiaDaSemana().equals(hoje))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Sem aulas hoje.")));
        }

//        AulasDoDia aulasDoDia = aulasDoDiaOpt.orElseGet(() -> horarioSemana.getListaDeAulasDoDia().stream()
//                .filter(dia -> dia.getDiaDaSemana().equals(hoje))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Sem aulas hoje.")));


        for (Aula aula : aulasDoDiaOpt.get().getAulas()) {
            int ordem = aula.getOrdem();
            int aulasAteIntervalo;

            if (subTurma.getTurma().getCurso().getTipoDeCurso().equals(TipoDeCurso.CAI)){
                aulasAteIntervalo = 2;
            }else{
                aulasAteIntervalo = 3;
            }
            LocalTime inicioAula = horarioEntrada.plusMinutes((ordem - 1) * minutosPorAula + Math.max(0, ordem - 1) * minutosDeIntervalo);

            if (horarioEntrada.plusMinutes(minutosPorAula*aulasAteIntervalo).isAfter(inicioAula)){
                inicioAula.plusMinutes(minutosDeIntervalo);
            }
            LocalTime fimAula = inicioAula.plusMinutes(minutosPorAula);

            if (agora.isAfter(inicioAula) && agora.isBefore(fimAula)) {
                return aula;
            }
        }

        throw new RuntimeException("Nenhuma aula acontecendo neste momento.");
    }

}
