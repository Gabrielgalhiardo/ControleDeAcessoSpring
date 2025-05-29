package com.senai.controle_de_acesso_spring.domain.repository;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
