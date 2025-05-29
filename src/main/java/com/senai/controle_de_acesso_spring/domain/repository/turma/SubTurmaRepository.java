package com.senai.controle_de_acesso_spring.domain.repository.turma;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubTurmaRepository extends JpaRepository<SubTurma, Long> {

}
