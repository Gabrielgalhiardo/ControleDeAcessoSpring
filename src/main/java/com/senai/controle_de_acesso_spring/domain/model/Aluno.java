package com.senai.controle_de_acesso_spring.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Aluno extends Usuario{

    @Column(nullable = false)
    private int idade;

    @OneToMany(mappedBy = "unidades_curriculares", cascade = CascadeType.ALL)
    private List<UnidadesCurriculares> unidadesCurriculares;



}
