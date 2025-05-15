package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@DiscriminatorValue("ALUNO")
public class Aluno extends Usuario {
    @OneToMany(mappedBy = "aluno")
    protected List<Ocorrencia> ocorrencias;

    @OneToMany(mappedBy = "aluno")
    protected List<Justificativa> justificativas;

    @ManyToMany
    protected List<SubTurma> subTurmas;
}