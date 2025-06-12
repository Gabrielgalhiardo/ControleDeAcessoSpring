package com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Ambiente;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ordem;

    @ManyToOne(cascade = CascadeType.ALL)
    private AulasDoDia aulasDia;

    @ManyToOne(cascade = CascadeType.ALL)
    private UnidadeCurricular unidadeCurricular;

    @ManyToOne(cascade = CascadeType.ALL)
    private Professor professor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ambiente ambiente;
}