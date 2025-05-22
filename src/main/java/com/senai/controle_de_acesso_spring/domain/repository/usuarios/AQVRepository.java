package com.senai.controle_de_acesso_spring.domain.repository.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.AQV;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AQVRepository extends JpaRepository<AQV, Long> {
    List<AQV> findByStatusDoUsuario(StatusDoUsuario statusDoUsuario);
}
