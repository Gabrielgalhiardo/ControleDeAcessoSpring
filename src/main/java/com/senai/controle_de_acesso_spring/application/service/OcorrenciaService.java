package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.OcorrenciaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.UsuarioRepository;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OcorrenciaService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public void criarOcorrenciaAtraso(String idAcesso) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdAcesso(idAcesso);
        if (usuarioOpt.isPresent()){
            if (usuarioOpt.get()instanceof Aluno aluno){
                System.out.println("o usuario " + aluno.getNome() + " é aluno!");
            }
        }
    }


}
