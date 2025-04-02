package com.senai.controle_de_acesso_spring.service;

import com.senai.controle_de_acesso_spring.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    List<Usuario> listaDeUsuarios = new ArrayList<>();

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
