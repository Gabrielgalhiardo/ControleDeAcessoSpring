package com.senai.controle_de_acesso_spring.domain.repository;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OcorrenciaRepository extends JpaRepository {
    List<Ocorrencia> findByStatusDoUsuario (StatusDoUsuario statusDoUsuario);
}
