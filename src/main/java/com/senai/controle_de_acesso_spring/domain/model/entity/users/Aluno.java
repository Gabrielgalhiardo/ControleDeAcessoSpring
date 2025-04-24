package com.senai.controle_de_acesso_spring.domain.model.entity.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.Turma;
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

    @ManyToMany(mappedBy = "alunos", cascade = CascadeType.ALL)
    private List<Turma> turma;

}
