package com.senai.controle_de_acesso_spring.domain.model.entity.usuarios;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nome;
    protected String cpf;
    protected LocalDate dataNascimento;
    protected String idAcesso;
    protected String email;
    protected String senha;
    @Enumerated(EnumType.STRING)
    protected StatusDoUsuario statusDoUsuario;

    @ElementCollection(fetch = FetchType.EAGER)
    protected List<String> permissoes;
}