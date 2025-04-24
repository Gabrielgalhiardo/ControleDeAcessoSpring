package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.entity.users.Aluno;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nomeDaTurma;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToMany
    @JoinTable(
            name = "turma_aluno",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;
}
