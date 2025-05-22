package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Usuario{
    public Professor(){}

    public Professor(Long id, String nome, String cpf, LocalDate dataNascimento, String idAcesso, String email, String senha, StatusDoUsuario statusDoUsuario, List<String> permissoes) {
        super(id, nome, cpf, dataNascimento, idAcesso, email, senha, statusDoUsuario, permissoes);
    }
}