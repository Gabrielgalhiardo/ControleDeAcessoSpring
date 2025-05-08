package com.senai.controle_de_acesso_spring.domain.model.entity.curso;

import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeCurso;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private TipoDeCurso tipoDeCurso;

    private Integer cargaHoraria;
    private Integer toleranciaMinutos;
    private Integer quantidadeDeSemestres;

    @OneToMany(mappedBy = "curso")
    private List<UnidadeCurricular> unidadesCurriculares;
}
