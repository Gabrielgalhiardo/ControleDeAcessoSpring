package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

@Service
public class AulaService {

    public static Aula pegarAulaAtualPelaSubTurma(SubTurma subTurma) {
        LocalTime horarioEntrada = subTurma.getTurma().getHorarioEntrada();
        int minutosPorAula = subTurma.getTurma().getCurso().getTipoDeCurso().getMinutosPorAula();
        int minutosDeIntervalo = subTurma.getTurma().getCurso().getTipoDeCurso().getIntevarloMinutos();

        DayOfWeek hoje = DayOfWeek.from(LocalDate.now());

        Semestre semestre = subTurma.getSemestres().stream()
                .max(Comparator.comparing(Semestre::getNumero))
                .orElseThrow(() -> new RuntimeException("Semestre não encontrado."));

        HorarioPadrao horario = semestre.getHorarioPadrao();
        AulasDoDia aulasDoDia = horario.getListaDeAulasDoDia().stream()
                .filter(dia -> dia.getDiaDaSemana().equals(hoje))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sem aulas hoje."));

        LocalTime agora = LocalTime.now();

        for (Aula aula : aulasDoDia.getAulas()) {
            int ordem = aula.getOrdem();
            LocalTime inicioAula = horarioEntrada.plusMinutes((ordem - 1) * minutosPorAula + Math.max(0, ordem - 1) * minutosDeIntervalo);
            LocalTime fimAula = inicioAula.plusMinutes(minutosPorAula);

            if (agora.isAfter(inicioAula) && agora.isBefore(fimAula)) {
                return aula;
            }
        }

        throw new RuntimeException("Nenhuma aula acontecendo neste momento.");
    }
}
