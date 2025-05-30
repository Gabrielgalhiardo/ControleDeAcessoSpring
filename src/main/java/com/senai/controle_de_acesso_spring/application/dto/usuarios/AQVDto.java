package com.senai.controle_de_acesso_spring.application.dto.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.AQV;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;

import java.time.LocalDate;

public record AQVDto(
        Long id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento
){
      public static AQVDto toDTO(AQV a) {
      return new AQVDto(a.getId(),a.getNome(), a.getCpf(),a.getEmail(),a.getDataNascimento());
      }

      public AQV fromDTO(){
              AQV aqv = new AQV();
              aqv.setId(id);
              aqv.setNome(nome);
              aqv.setCpf(cpf);
              aqv.setEmail(email);
              aqv.setDataNascimento(dataNascimento);
              aqv.setStatusDoUsuario(StatusDoUsuario.ATIVO);
              return aqv;
      }
}
