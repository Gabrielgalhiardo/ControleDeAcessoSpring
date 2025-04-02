package com.senai.controle_de_acesso_spring.model;
import lombok.Data;
import java.util.UUID;

@Data
public class Usuario {

    private long id;
    private UUID idAcesso;
    private String nome;
    private String telefone;
    private String email;
    private String caminhoImagem;

}
