package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.EntradaSaida;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeJustificativa;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Data
public class Justificativas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Date dataInicio;

    @Column(nullable = false)
    private int quantidadeDeDias;

    private String urlAnexo;

    @Enumerated(EnumType.STRING)
    private TipoDeJustificativa tipoDeJustificativa;

    @Column(nullable = false)
    private boolean status;

    @OneToOne
    private EntradaSaida entradaSaida;

}
