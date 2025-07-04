package com.senai.controle_de_acesso_spring.domain.repository.usuarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByStatusDoUsuario(StatusDoUsuario statusDoUsuario);
    Optional<Usuario> findByIdAcesso(String idAcesso);
    @Query("SELECT u FROM Usuario u WHERE u.email = :login OR u.cpf = :login")
    Optional<Usuario> findByEmailOrCpf(@Param("login") String login);
}
