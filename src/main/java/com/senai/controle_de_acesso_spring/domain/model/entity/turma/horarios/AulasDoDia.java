package com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.enums.DiaDaSemana;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
public class AulasDoDia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JoinColumn(nullable = true)
    private DiaDaSemana diaDaSemana;

    @ManyToOne(cascade = CascadeType.ALL)
    private HorarioBase horario;

    @OneToMany(mappedBy = "aulasDia", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "ordem")
    private List<Aula> aulas;
}

