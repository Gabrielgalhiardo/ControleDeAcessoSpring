package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("ALUNO")
public class Aluno extends Usuario {
    @OneToMany(mappedBy = "aluno")
    private List<Ocorrencia> ocorrencias;

    @OneToMany(mappedBy = "aluno")
    private List<Justificativa> justificativas;

    @ManyToMany
    private List<SubTurma> subTurmas;
}