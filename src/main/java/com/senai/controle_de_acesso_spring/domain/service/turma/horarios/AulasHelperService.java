package com.senai.controle_de_acesso_spring.domain.service.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.enums.DiaDaSemana;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AulasHelperService {

    public AulasDoDia buscarAulasDoDia(Semestre semestre, LocalDate semana, DiaDaSemana diaAtual) {
        return semestre.getHorariosSemanais().stream()
                .filter(hs -> hs.getSemanaReferencia().equals(semana))
                .findFirst()
                .flatMap(hs -> hs.getListaDeAulasDoDia().stream()
                        .filter(ad -> ad.getDiaDaSemana().equals(diaAtual))
                        .findFirst())
                .or(() -> Optional.ofNullable(semestre.getHorarioPadrao())
                        .flatMap(hp -> hp.getListaDeAulasDoDia().stream()
                                .filter(ad -> ad.getDiaDaSemana().equals(diaAtual))
                                .findFirst()))
                .orElseThrow(() -> new RuntimeException("Sem aulas hoje."));
    }
}
