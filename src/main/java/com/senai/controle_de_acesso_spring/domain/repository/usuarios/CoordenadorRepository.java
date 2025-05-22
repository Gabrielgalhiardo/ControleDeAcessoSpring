package com.senai.controle_de_acesso_spring.domain.repository.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Coordenador;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    List<Coordenador> findByStatusDoUsuario(StatusDoUsuario statusDoUsuario);
}
