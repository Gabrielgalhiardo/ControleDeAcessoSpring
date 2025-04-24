package com.senai.controle_de_acesso_spring.domain.model.entity;

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

    @Column(nullable = false)
    private Date dataInicio;

    @Column(nullable = false)
    private Date dataTermino;

    @ManyToMany
    @JoinTable(
            name = "curso_unidades_curriculares",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "unidade_curricular_id")
    )
    private List<UnidadesCurriculares> unidadesCurriculares;
}
