package com.senai.controle_de_acesso_spring.domain.repository.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import lombok.Locked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioPadraoRepository extends JpaRepository<HorarioPadrao, Long> {
}
