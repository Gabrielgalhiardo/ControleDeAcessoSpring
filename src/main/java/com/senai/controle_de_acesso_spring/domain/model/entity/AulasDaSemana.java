package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.enums.DiaDaSemana;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDoCurso;
import jakarta.persistence.*;

import java.util.List;

public class AulasDaSemana {

    @Enumerated(EnumType.STRING)
    private DiaDaSemana diaDaSemana;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL)
    @JoinColumn(name = "aula_id")
    private List<Aula> listaDeAulas;



}
