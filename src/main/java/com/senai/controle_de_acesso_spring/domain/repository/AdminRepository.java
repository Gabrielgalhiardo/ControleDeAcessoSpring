package com.senai.controle_de_acesso_spring.domain.repository;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Admin;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByStatusDoUsuario(StatusDoUsuario statusDoUsuario);
}
