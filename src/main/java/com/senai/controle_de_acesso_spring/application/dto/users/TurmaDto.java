package com.senai.controle_de_acesso_spring.application.dto.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurmaDto(Long id,
                       String nome,
                       String periodo,
                       LocalDate dataInicial,
                       LocalTime horarioEntrada,
                       Integer qtdSemestres,
                       Integer qtdAulasPorDia) {
}