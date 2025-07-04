package com.senai.controle_de_acesso_spring.domain.model.entity.turma;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Curso;
import com.senai.controle_de_acesso_spring.domain.model.enums.Periodo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siglaDaTurma;

    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    private LocalDate dataInicial;
    private LocalTime horarioEntrada;
    private Integer qtdSemestres;
    private Integer qtdAulasPorDia;

    @ManyToOne
    private Curso curso;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTurma> subTurmas;
}