package com.senai.controle_de_acesso_spring.domain.model.entity.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.UnidadesCurriculares;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Professor extends Usuario {


    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<UnidadesCurriculares> unidadesCurriculares;

}
