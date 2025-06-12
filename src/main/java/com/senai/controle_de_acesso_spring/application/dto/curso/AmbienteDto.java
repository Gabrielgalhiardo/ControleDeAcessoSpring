package com.senai.controle_de_acesso_spring.application.dto.curso;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Ambiente;

public record AmbienteDto(
    Long id,
    String nome,
    boolean ativo
) {
    public static AmbienteDto toDTO(Ambiente a) {
        return new AmbienteDto(a.getId(), a.getNome(), a.isAtivo());
    }

    public Ambiente fromDTO() {
        Ambiente ambiente = new Ambiente();
        ambiente.setId(id);
        ambiente.setNome(nome);
        ambiente.setAtivo(ativo);
        return ambiente;
    }
}
