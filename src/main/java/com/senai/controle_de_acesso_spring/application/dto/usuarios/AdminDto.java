package com.senai.controle_de_acesso_spring.application.dto.usuarios;


import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Admin;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.time.LocalDate;

public record AdminDto(Long id,
         String nome,
         String cpf,
         String email,
         LocalDate dataNascimento
) {
    public static AdminDto toDTO(Admin a){
        return new AdminDto(a.getId(), a.getNome(), a.getCpf(), a.getEmail(), a.getDataNascimento());
    }

    public Admin fromDTO(){
        Admin admin = new Admin();
        admin.setId(id);
        admin.setNome(nome);
        admin.setCpf(cpf);
        admin.setEmail(email);
        admin.setDataNascimento(dataNascimento);
        admin.setStatusDoUsuario(StatusDoUsuario.ATIVO);
        return admin;
    }
}
