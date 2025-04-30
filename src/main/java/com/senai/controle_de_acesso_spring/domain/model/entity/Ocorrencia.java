package com.senai.controle_de_acesso_spring.domain.model.entity;

import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDoCurso;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusDaOcorrencia statusDaOcorrencia;

    @Enumerated(EnumType.STRING)
    private TipoDoCurso tipoDoCurso;

    //Depois apagar essa mensagem e colocar relacionamento com Aluno
}
