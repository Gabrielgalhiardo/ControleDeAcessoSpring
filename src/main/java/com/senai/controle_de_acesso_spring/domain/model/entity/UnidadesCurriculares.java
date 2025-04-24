package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.entity.users.Professor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class UnidadesCurriculares {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nomeUnidadeCurricular;

    @ManyToMany(mappedBy = "unidadesCurriculares", cascade = CascadeType.ALL)
    private List<Curso> curso;

    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    private Professor professor;
}
