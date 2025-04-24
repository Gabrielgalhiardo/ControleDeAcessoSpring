package com.senai.controle_de_acesso_spring.domain.model.entity.users;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@MappedSuperclass
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID idAcesso;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private StatusDoUsuario statusDoUsuario;

    private String telefoneCelular;

    private String telefoneFixo;

    private String caminhoImagem;

}
