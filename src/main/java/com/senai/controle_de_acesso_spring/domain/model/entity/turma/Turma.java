package com.senai.controle_de_acesso_spring.domain.model.entity.turma;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Curso;
import com.senai.controle_de_acesso_spring.domain.model.enums.Periodo;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    private LocalDate dataInicial;
    private LocalTime horarioEntrada;
    private Integer qtdSemestres;
    private Integer qtdAulasPorDia;

    @ManyToOne
    private Curso curso;

    @OneToMany(mappedBy = "turma")
    private List<SubTurma> subTurmas;
}