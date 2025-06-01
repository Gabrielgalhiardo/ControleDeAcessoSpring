package com.senai.controle_de_acesso_spring.domain.model.entity.curso;

import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeCurso;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private TipoDeCurso tipoDeCurso;

    private Integer cargaHoraria;
    private Integer toleranciaMinutos;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<UnidadeCurricular> unidadesCurriculares;
}
