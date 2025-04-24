package com.senai.controle_de_acesso_spring.domain.model.entity.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.UnidadesCurriculares;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Professor extends Usuario {

    @ElementCollection
    @CollectionTable(name = "turmas", joinColumns = @JoinColumn(name = "turmas_id"))
    private List<String> turma;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<UnidadesCurriculares> unidadesCurriculares;

}
