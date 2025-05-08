package com.senai.controle_de_acesso_spring.domain.model.entity.curso;

import jakarta.persistence.*;

@Entity
public class Ambiente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD:src/main/java/com/senai/controle_de_acesso_spring/domain/model/entity/Aula.java
    @ManyToOne
    private Aula aula;
}
=======
    private String nome;
}
>>>>>>> igor:src/main/java/com/senai/controle_de_acesso_spring/domain/model/entity/curso/Ambiente.java
