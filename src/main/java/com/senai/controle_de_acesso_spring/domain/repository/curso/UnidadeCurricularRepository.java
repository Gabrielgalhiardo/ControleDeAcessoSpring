package com.senai.controle_de_acesso_spring.domain.repository.curso;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeCurricularRepository extends JpaRepository<UnidadeCurricular, Long> {
}
