package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDoCurso;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nomeDoCurso;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "curso_unidades_curriculares",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "unidades_curriculares_id")
    )
    private List<UnidadesCurriculares> unidadesCurriculares;

    private double cargaHoraria;

    @Enumerated(EnumType.STRING)
    private TipoDoCurso tipoDoCurso;

    private int quantidadeDeSemestres;

    private double tolerancia;
}
