package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
@DiscriminatorValue("COORDENADOR")
public class Coordenador extends Usuario{
    @OneToMany
    private List<Professor> equipeProfessores;
}