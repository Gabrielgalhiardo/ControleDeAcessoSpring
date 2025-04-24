package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.entity.users.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.Periodo;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nomeDaTurma;

    @OneToMany(mappedBy = "listaDeSubTurma", cascade = CascadeType.ALL)
    private List<SubTurma> listaDeSubTurma;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(nullable = false)
    private Date dataInicio;

    private Time horarioDeEntrada;

    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    private int quantidadeDeAulas;




}
