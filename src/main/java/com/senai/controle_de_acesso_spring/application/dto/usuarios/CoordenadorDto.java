package com.senai.controle_de_acesso_spring.application.dto.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Coordenador;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.time.LocalDate;
import java.util.List;

public record CoordenadorDto(
        Long id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        List<Professor> equipeProfessores
) {
    public static CoordenadorDto toDTO(Coordenador c) {
    return new CoordenadorDto(c.getId(), c.getNome(), c.getCpf(), c.getEmail(), c.getDataNascimento(), c.getEquipeProfessores());
    }

    public Coordenador fromDTO() {
        Coordenador coordenador = new Coordenador();
        coordenador.setId(id);
        coordenador.setNome(nome);
        coordenador.setCpf(cpf);
        coordenador.setEmail(email);
        coordenador.setDataNascimento(dataNascimento);
        coordenador.setStatusDoUsuario(StatusDoUsuario.ATIVO);
        coordenador.setIdAcesso("");
        coordenador.setSenha("");
        coordenador.setEquipeProfessores(equipeProfessores);
     return coordenador;
    }
}
