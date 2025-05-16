package com.senai.controle_de_acesso_spring.application.dto.users;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Ambiente;

public record AmbienteDto(
    Long id,
    String nome
) {
    public static AmbienteDto toDTO(Ambiente a) {
        return new AmbienteDto(a.getId(), a.getNome());
    }

    public Ambiente fromDTO() {
        Ambiente ambiente = new Ambiente();
        ambiente.setId(id);
        ambiente.setNome(nome);
        return ambiente;
    }
}
