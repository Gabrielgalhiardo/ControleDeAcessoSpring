package com.senai.controle_de_acesso_spring.application.dto.auth;

import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeUsuario;

public record RegisterDTO(
        String nome,
        String username,
        String password,
        TipoDeUsuario tipoDeUsuario
) {

}
