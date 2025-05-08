package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Usuario{
}