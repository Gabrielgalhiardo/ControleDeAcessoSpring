package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Aluno extends Usuario {

    @Column(nullable = false)
    private int idade;

    @Column(nullable = false)
    private Date dataDeNascimento;

    @ManyToMany(mappedBy = "alunos")
    private List<SubTurma> subturmas;

}
