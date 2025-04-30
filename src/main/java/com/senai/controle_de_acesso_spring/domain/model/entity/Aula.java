package com.senai.controle_de_acesso_spring.domain.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Aula aula;
}