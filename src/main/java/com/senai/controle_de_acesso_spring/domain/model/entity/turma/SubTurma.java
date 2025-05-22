package com.senai.controle_de_acesso_spring.domain.model.entity.turma;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SubTurma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @ManyToMany(mappedBy = "subTurmas")
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "subTurma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Semestre> semestres;
}