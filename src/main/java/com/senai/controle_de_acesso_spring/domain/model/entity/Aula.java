package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.entity.users.Ambiente;
import com.senai.controle_de_acesso_spring.domain.model.entity.users.Professor;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private UnidadesCurriculares unidadesCurriculares;

    @OneToOne
    private Professor professor;

    @OneToOne
    private Ambiente ambiente;

}