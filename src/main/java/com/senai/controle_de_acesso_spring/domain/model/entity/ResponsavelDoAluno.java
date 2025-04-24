package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.entity.users.Aluno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsavelDoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefoneCelular;

    private String telefoneFixo;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;

    @ManyToMany
    @JoinTable(
            name = "responsavel_aluno",
            joinColumns = @JoinColumn(name = "responsavel_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;

}
