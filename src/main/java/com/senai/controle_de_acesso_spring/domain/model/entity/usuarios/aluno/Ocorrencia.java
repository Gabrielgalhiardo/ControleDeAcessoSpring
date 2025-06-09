package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeOcorrencia;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDeOcorrencia tipo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusDaOcorrencia statusDaOcorrencia;

    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraConclusao;

    @ManyToOne
    @JsonBackReference
    private Aluno aluno;

    @ManyToOne
    private Professor professorResponsavel;

    @ManyToOne
    private UnidadeCurricular unidadeCurricular;
}