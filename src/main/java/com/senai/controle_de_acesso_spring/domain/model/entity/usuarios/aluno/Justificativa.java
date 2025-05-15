package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaJustificativa;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeJustificativa;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Justificativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDeJustificativa tipoDeJustificativa;

    private String descricao;
    private String anexo;
    private LocalDate dataInicial;
    private Integer qtdDias;

    @Enumerated(EnumType.STRING)
    private StatusDaJustificativa statusDaJustificativa;

    @ManyToOne
    private Aluno aluno;
}