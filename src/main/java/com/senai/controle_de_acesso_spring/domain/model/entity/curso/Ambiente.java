package com.senai.controle_de_acesso_spring.domain.model.entity.curso;

import jakarta.persistence.*;

@Entity
public class Ambiente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
