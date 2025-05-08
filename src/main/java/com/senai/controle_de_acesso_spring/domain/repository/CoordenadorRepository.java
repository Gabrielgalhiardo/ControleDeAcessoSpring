package com.senai.controle_de_acesso_spring.domain.repository;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
}
