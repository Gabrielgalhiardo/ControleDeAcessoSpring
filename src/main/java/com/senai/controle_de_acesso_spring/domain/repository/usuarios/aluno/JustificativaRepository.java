package com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Justificativa;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaJustificativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificativaRepository extends JpaRepository<Justificativa, Long> {
    List<Justificativa> findByStatusDaJustificativa (StatusDaJustificativa statusDaJustificativa);
}
