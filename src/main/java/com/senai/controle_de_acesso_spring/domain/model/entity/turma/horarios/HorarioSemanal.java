package com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class HorarioSemanal extends HorarioBase {

    private LocalDate semanaReferencia;

//    @ManyToOne
//    @JoinColumn(name = "semestre_id")
//    private Semestre semestre;
}