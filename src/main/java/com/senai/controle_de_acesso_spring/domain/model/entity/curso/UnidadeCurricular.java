package com.senai.controle_de_acesso_spring.domain.model.entity.curso;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class UnidadeCurricular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeUnidadeCurricular;
    private Integer cargaHorariaTotal;

    @ElementCollection
    private Map<Integer, Integer> cargaHorariaPorSemestre;

    @ManyToOne
    private Curso curso;

    @ManyToMany
    private List<Professor> professores;
}
