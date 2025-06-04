package com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class HorarioPadrao extends HorarioBase {
    @OneToOne(mappedBy = "horarioPadrao", cascade = CascadeType.ALL)
    private Semestre semestre;
}