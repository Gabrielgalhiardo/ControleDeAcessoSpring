package com.senai.controle_de_acesso_spring.domain.model.enums;

public enum TipoDeCurso {
    CAI(20, 50),
    TECNICO(1,1);

    private final int intevarloMinutos;
    private final int minutosPorAula;

    TipoDeCurso(int intevarloMinutos, int minutosPorAula) {
        this.intevarloMinutos = intevarloMinutos;
        this.minutosPorAula = minutosPorAula;
    }

    public int getIntevarloMinutos() {
        return intevarloMinutos;
    }

    public int getMinutosPorAula() {
        return minutosPorAula;
    }
}
