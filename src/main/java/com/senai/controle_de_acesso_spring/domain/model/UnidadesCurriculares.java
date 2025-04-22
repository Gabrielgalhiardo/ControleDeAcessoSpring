package com.senai.controle_de_acesso_spring.domain.model;

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

    private List<String> curso;
}
