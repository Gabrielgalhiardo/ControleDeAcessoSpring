package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.entity.users.Aluno;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class SubTurma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;



    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToMany
    @JoinTable(
            name = "sub_turma_aluno",
            joinColumns = @JoinColumn(name = "sub_turma_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;
}
