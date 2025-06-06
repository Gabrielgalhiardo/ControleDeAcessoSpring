package com.senai.controle_de_acesso_spring.domain.model.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum DiaDaSemana {
    SEGUNDA,
    TERCA,
    QUARTA,
    QUINTA,
    SEXTA;


    public static DiaDaSemana obterDiaDaSemanaAtual() {
        DayOfWeek hoje = DayOfWeek.from(LocalDate.now());

        return switch (hoje) {
            case MONDAY -> DiaDaSemana.SEGUNDA;
            case TUESDAY -> DiaDaSemana.TERCA;
            case WEDNESDAY -> DiaDaSemana.QUARTA;
            case THURSDAY -> DiaDaSemana.QUINTA;
            case FRIDAY -> DiaDaSemana.SEXTA;
            default -> throw new IllegalArgumentException("Fim de semana não permitido: " + hoje);
        };
    }


}
