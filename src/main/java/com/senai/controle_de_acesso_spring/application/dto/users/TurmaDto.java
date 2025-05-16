package com.senai.controle_de_acesso_spring.application.dto.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.model.enums.Periodo;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurmaDto(Long id,
                       String nome,
                       Periodo periodo,
                       LocalDate dataInicial,
                       LocalTime horarioEntrada,
                       Integer qtdSemestres,
                       Integer qtdAulasPorDia) {
    public static TurmaDto toDTO(Turma t){
        return new TurmaDto(t.getId(), t.getNome(), t.getPeriodo(), t.getDataInicial(),t.getHorarioEntrada(), t.getQtdSemestres(), t.getQtdAulasPorDia());
    }

    public Turma fromDTO(){
        Turma t = new Turma();
        t.setId(id);
        t.setNome(nome);
        t.setPeriodo(periodo);
        t.setDataInicial(dataInicial);
        t.setHorarioEntrada(horarioEntrada);
        t.setQtdSemestres(qtdSemestres);
        t.setQtdAulasPorDia(qtdAulasPorDia);

        return t;
    }
}