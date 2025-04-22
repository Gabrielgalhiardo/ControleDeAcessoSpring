package com.senai.controle_de_acesso_spring.domain.service;

import com.senai.controle_de_acesso_spring.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final List<Usuario> listaDeUsuarios;

    public void criarUsuario(Usuario usuario){
        listaDeUsuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios(){
        return listaDeUsuarios;
    }

    public Usuario listarUsuarioPeloId(long id){
        for (Usuario usuario:listaDeUsuarios){
            if (usuario.getId() == id){
                return usuario;
            }
        }
        return null;
    }

    public void deletarUsuario(long id){
        for (Usuario usuario:listaDeUsuarios){
            if (usuario.getId() == id){
                listaDeUsuarios.remove(usuario);
            }
        }
    }

    public void atualizarUsuario(Usuario usuarioNovo, long id){
        int indexUsuario = listaDeUsuarios.indexOf(listaDeUsuarios.stream().filter(u -> u.getId() == id).findFirst().orElseThrow());
        listaDeUsuarios.set(indexUsuario, usuarioNovo);
    }

}
