package com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,        // Utiliza um identificador baseado no nome da classe
        include = JsonTypeInfo.As.PROPERTY, // Inclui o tipo como uma propriedade no JSON
        property = "type"                  // Nome da propriedade no JSON que armazenará o tipo
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HorarioSemanal.class, name = "horarioSemanal"),
        @JsonSubTypes.Type(value = HorarioPadrao.class, name = "horarioPadrao")
})

public abstract class HorarioBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    private Semestre semestre;

    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<AulasDoDia> listaDeAulasDoDia;

}
