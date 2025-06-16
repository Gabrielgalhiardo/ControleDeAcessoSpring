package com.senai.controle_de_acesso_spring.domain.repository.curso;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeCurricularRepository extends JpaRepository<UnidadeCurricular, Long> {

    @Query("SELECT uc FROM UnidadeCurricular uc JOIN uc.professores p WHERE p.id = :professorId")
    Optional<UnidadeCurricular> findByProfessorId(Long professorId);
}
